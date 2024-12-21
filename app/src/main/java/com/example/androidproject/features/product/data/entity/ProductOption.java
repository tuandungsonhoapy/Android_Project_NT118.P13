package com.example.androidproject.features.product.data.entity;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class ProductOption implements Parcelable {
    private String chip;
    private String ram;
    private String rom;
    private int quantity;

    public ProductOption() {
        this.chip = "";
        this.ram = "";
        this.rom = "";
        this.quantity = 0;
    }

    public ProductOption(String chip, String ram, String rom, int quantity) {
        this.chip = chip;
        this.ram = ram;
        this.rom = rom;
        this.quantity = quantity;
    }

    protected ProductOption(Parcel in) {
        chip = in.readString();
        ram = in.readString();
        rom = in.readString();
        quantity = in.readInt();
    }

    public static final Creator<ProductOption> CREATOR = new Creator<ProductOption>() {
        @Override
        public ProductOption createFromParcel(Parcel in) {
            return new ProductOption(in);
        }

        @Override
        public ProductOption[] newArray(int size) {
            return new ProductOption[size];
        }
    };

    public String getChip() {
        return chip;
    }

    public void setChip(String chip) {
        this.chip = chip;
    }

    public String getRam() {
        return ram;
    }

    public void setRam(String ram) {
        this.ram = ram;
    }

    public String getRom() {
        return rom;
    }

    public void setRom(String rom) {
        this.rom = rom;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(chip);
        parcel.writeString(ram);
        parcel.writeString(rom);
        parcel.writeInt(quantity);
    }
}
