package com.test.base;

import java.lang.Integer;

public class bilder {
    public final Integer id;

    public static class Bilder {
        private Integer id;
        private String Name;

        public Bilder(Integer ID) {
            this.id = ID;
        }

        public Bilder resetId(Integer ID) {
            this.id = ID;
            return this;
        }

        public Bilder setName(String N) {
            this.Name = N;
            return this;
        }

        public bilder bild() {
            return new bilder(this);
        }

    }

    public bilder(Bilder b) {
        id = b.id;
    }
}
