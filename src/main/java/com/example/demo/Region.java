package com.example.demo;

import java.util.Objects;

public class Region {
    private String name;

    public Region(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Region region = (Region) o;
        return Objects.equals(name, region.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
