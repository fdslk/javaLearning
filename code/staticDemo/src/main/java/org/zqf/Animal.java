package org.zqf;

public class Animal {
    private String eye;
    private String nose;
    private String mouth;

    public static class Builder {
        private String eye;
        private String nose;
        private String mouth;

        public Builder(String s) {
            this.eye = s;
        }

        public Builder nose(String f) {
            this.nose = f;
            return this;
        }

        public Builder mouth(String c) {
            this.mouth = c;
            return this;
        }

        public Animal build() {
            return new Animal(this);
        }
    }

    private Animal(Builder builder) {
        this.eye = builder.eye;
        this.nose = builder.nose;
        this.mouth = builder.mouth;
    }
}
