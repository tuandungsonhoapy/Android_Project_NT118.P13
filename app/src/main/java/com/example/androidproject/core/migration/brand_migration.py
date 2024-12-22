import pandas as pd
import numpy as np
import firebase_admin
from firebase_admin import credentials, firestore
from datetime import datetime

cred = credentials.Certificate("/mnt/c/Users/Hello/Downloads/credencials.json")
firebase_admin.initialize_app(cred)
db = firestore.client()

file_path = "/mnt/c/Users/Hello/Downloads/project android.xlsx"
sheets = pd.ExcelFile(file_path).sheet_names

def process_data(data):
    brands = []
    current_brand = None

    for _,row in data.iterrows():
        field = row['field']
        value = row['value']

        if field == 'createdAt':
            if current_brand is not None:
                brands.append(current_brand)
            current_brand = {"createdAt": value}

        elif field == 'updatedAt':
            if current_brand is not None:
                current_brand["updatedAt"] = value if not pd.isna(value) else None
                brands.append(current_brand)
                current_brand = None

        elif current_brand is not None:
            if field == 'id':
                current_brand["id"] = value
            elif field == 'name':
                current_brand["name"] = value
            elif field == 'description':
                current_brand["description"] = value
            elif field == 'imageUrl':
                current_brand["imageUrl"] = value
            elif field == 'hidden':
                current_brand["hidden"] = value

    return brands


def normalize_data(data):
    if isinstance(data, dict):
        return {str(k): normalize_data(v) for k, v in data.items()}
    elif isinstance(data, list):
        return [normalize_data(i) for i in data]
    elif pd.isna(data):
        return None
    else:
        return data

data = pd.read_excel(file_path, sheet_name="Brand", usecols=[1, 2], header=None)
data.columns = ['field', 'value']

try:
    brands = process_data(data)

    for brand in brands:
        brand = normalize_data(brand)
        db.collection('brands').document(brand['id']).set(brand)
        print("Đẩy dữ liệu thành công")
except Exception as e:
    print("Có lỗi xảy ra:", e)
    print("Dừng chương trình")
    exit(1)