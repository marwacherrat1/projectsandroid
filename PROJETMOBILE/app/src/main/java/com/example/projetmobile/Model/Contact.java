package com.example.projetmobile.Model;
public class Contact {

        private String nomContact;
        private String prenomContact;
        private String serviceContact;
        private String emailContact;

        private String img_url;

        public Contact(String nomContact, String prenomContact, String serviceContact,
                       String emailContact, String img_url){
                this.nomContact=nomContact;
                this.prenomContact=prenomContact;
                this.img_url=img_url;
                this.serviceContact=serviceContact;
                this.emailContact=emailContact;
        }
        public String getNomContact() {
                return nomContact;
        }

        public void setNomContact(String nomContact) {
                this.nomContact = nomContact;
        }

        public String getPrenomContact() {
                return prenomContact;
        }

        public void setPrenomContact(String prenomContact) {
                this.prenomContact = prenomContact;
        }

        public String getServiceContact() {
                return serviceContact;
        }

        public void setServiceContact(String serviceContact) {
                this.serviceContact = serviceContact;
        }

        public String getEmailContact() {
                return emailContact;
        }

        public void setEmailContact(String emailContact) {
                this.emailContact = emailContact;
        }

        public String getImg_url() {
                return img_url;
        }

        public void setImg_url(String img_url) {
                this.img_url = img_url;
        }

}

