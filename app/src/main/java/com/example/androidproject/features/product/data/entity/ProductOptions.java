package com.example.androidproject.features.product.data.entity;

import java.util.ArrayList;
import java.util.List;

public class ProductOptions {
    private List<String> chip;
    private List<String> ram;
    private List<String> rom;

    public ProductOptions() {
        this.chip = new ArrayList<>();
        this.ram = new ArrayList<>();
        this.rom = new ArrayList<>();
    }

    public ProductOptions(List<String> chip, List<String> ram, List<String> rom) {
        this.chip = chip;
        this.ram = ram;
        this.rom = rom;
    }

    public List<String> getChip() {
        return chip;
    }

    public void setChip(List<String> chip) {
        this.chip = chip;
    }

    public List<String> getRam() {
        return ram;
    }

    public void setRam(List<String> ram) {
        this.ram = ram;
    }

    public List<String> getRom() {
        return rom;
    }

    public void setRom(List<String> rom) {
        this.rom = rom;
    }
}
