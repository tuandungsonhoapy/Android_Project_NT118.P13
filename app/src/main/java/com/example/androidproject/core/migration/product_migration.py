import pandas as pd
import firebase_admin
from firebase_admin import credentials, firestore

cred = credentials.Certificate("/mnt/c/Users/Hello/Downloads/credencials.json")
firebase_admin.initialize_app(cred)
db = firestore.client()

file_path = "/mnt/c/Users/Hello/Downloads/cc.xlsx"
sheets = pd.ExcelFile(file_path).sheet_names

def process_data(data):
    products = []
    current_product = None
    temp_images = {}
    temp_options = {}
    current_option_index = None

    for _, row in data.iterrows():
        field = row['field']
        value = row['value']
        detail_field = row.get('detail_field', None)
        detail_value = row.get('detail_value', None)

        if field == "createdAt":
            if current_product is not None:
                if temp_images:
                    current_product["images"] = [img for _, img in sorted(temp_images.items())]
                if temp_options:
                    current_product["options"] = [opt for _, opt in sorted(temp_options.items())]

                products.append(current_product)

            current_product = {
                "id": None,
                "createdAt": value,
                "images": [],
                "options": [],
            }
            temp_images = {}
            temp_options = {}
            current_option_index = None
            continue

        if current_product is None:
            continue

        if isinstance(value, str) and value.lower() == "null":
            continue

        if field == "images":
            img_index = int(value) if not pd.isna(value) else 0
            temp_images[img_index] = detail_field

        elif field == "options":
            if not pd.isna(value):
                current_option_index = int(value)
                temp_options.setdefault(current_option_index, {})
            elif pd.isna(value) and current_option_index is None:
                current_option_index = len(temp_options)
                temp_options.setdefault(current_option_index, {})

            if current_option_index is not None and not pd.isna(detail_field):
                temp_options[current_option_index][detail_field] = (
                    str(detail_value).strip() if detail_field != "quantity" else int(detail_value)
                )

        elif pd.isna(field) and not pd.isna(detail_field):
            if current_option_index is None:
                print("  Không có option hiện tại, bỏ qua thông tin chi tiết.")
            else:
                temp_options[current_option_index][detail_field] = (
                    str(detail_value).strip() if detail_field != "quantity" else int(detail_value)
                )

        elif field in ["id", "name", "brandId", "categoryId", "price", "rating", "description", "hidden", "stockQuantity"]:
            current_product[field] = value

        elif field == "updatedAt":
            current_product["updatedAt"] = value

    if current_product is not None:
        if temp_images:
            current_product["images"] = [img for _, img in sorted(temp_images.items())]
        if temp_options:
            current_product["options"] = [opt for _, opt in sorted(temp_options.items())]
        products.append(current_product)

    return products



def normalize_data(data):
    if isinstance(data, dict):
        return {str(k): normalize_data(v) for k, v in data.items()}
    elif isinstance(data, list):
        return [normalize_data(i) for i in data]
    elif pd.isna(data):
        return None
    else:
        return data

data = pd.read_excel(file_path, sheet_name="products", usecols=[1, 2, 3, 4], header=None)
data.columns = ['field', 'value', 'detail_field', 'detail_value']

try:
    products = process_data(data)

    if products:
        for product in products:
            product_id = product.get('id')
            if not product_id:
                print(f"Sản phẩm không có ID, bỏ qua... Sản phẩm: {product}")
                continue

            print(f"Đang xử lý sản phẩm ID: {product_id}")
            normalized_product = normalize_data(product)
            db.collection('products').document(product_id).set(normalized_product)
            print(f"Upload thành công: {product_id}")
    else:
        print("Không có sản phẩm hợp lệ.")
except Exception as e:
    print(f"Lỗi: {str(e)}")
    import traceback
    print(traceback.format_exc())
