package com.services.models;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;
    /**
     * FirstDream
     *
     * @author Jorge
     *
     */
    public class Token {

        private Integer id;
        private String token;
        private Date expira;

        public Token() {
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getToken() {
            if (expira.before(new Date())) {
                token = null;
            }
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public boolean isAlive() {
            return new Date().before(expira);
        }

        public Date getExpira() {
            return expira;
        }

        public void setExpira(Date expira) {
            this.expira = expira;
        }

        @Override
        public int hashCode() {
            int hash = 7;
            hash = 19 * hash + Objects.hashCode(this.token);
            return hash;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final Token other = (Token) obj;
            if (!Objects.equals(this.token, other.token)) {
                return false;
            }
            return true;
        }

        @Override
        public String toString() {
            return "Token{" + "token=" + token + ", expira=" + expira + '}';
        }

    }



