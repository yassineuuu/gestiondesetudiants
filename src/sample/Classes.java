package sample;

public class Classes {

    //Variables
        private int id;
        private String nom;
        private String Prenom;
        private String telephone;
        private String email;
        private String specialite;


    //Constructor

    public Classes(int id, String nom, String prenom, String telephone, String email, String specialite) {
        this.id = id;
        this.nom = nom;
        Prenom = prenom;
        this.telephone = telephone;
        this.email = email;
        this.specialite=specialite;

    }

    //Getters/Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return Prenom;
    }

    public void setPrenom(String prenom) {
        Prenom = prenom;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSpecialite() {
        return specialite;
    }

    public void setSpecialite(String specialite) {
        this.specialite = specialite;
    }
    //Methodes


}
