package sample;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    //Variables

    @FXML
    private VBox centerVBox;

    @FXML
    private Button listBtn;
    @FXML
    private Button btnFind;
    @FXML
    private Button addBtn;
    @FXML
    private Button removeBtn;
    @FXML
    private VBox leftVBox;

    private Label title = new Label();





    //get Students (Contenue du tableau)
    public ObservableList<Classes> getEtudiant(){
        ObservableList<Classes> etudiantList = FXCollections.observableArrayList();
        //Connexion
        Connection  cnx =ConnexionMySQL.cnx();
        PreparedStatement prepared;
        String query = "SELECT * FROM etudiants";

        Statement st;
        ResultSet rs;

        try{
            st = cnx.createStatement();
            rs = st.executeQuery(query);
            Classes etudiant;
            while(rs.next()){
                etudiant = new Classes(rs.getInt("id"), rs.getString("nom"),
                        rs.getString("prenom"), rs.getString("tel"),
                        rs.getString("email"),rs.getString("specialite"));
                etudiantList.add(etudiant);
            }

        }catch(Exception ex){
            ex.printStackTrace();
        }
        return etudiantList;
    }



    public void afficherList(){
        //Title
        centerVBox.getChildren().clear();
        title.setText("Tableau Des Etudiants");
        title.setTextFill(Color.WHITE);
        title.setFont(Font.font("Bodoni MT italic",36));
        title.setPadding(new Insets(0,0,30,0));
        centerVBox.getChildren().add(title);

        //Tableau
        TableView <Classes> tableView = new TableView<Classes>();
        tableView.setMaxHeight(300);
            //les colones du tableau
        TableColumn <Classes, Integer> id = new TableColumn<>();
        id.setMinWidth(38);
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        id.setText("ID");

        TableColumn <Classes, String> nom = new TableColumn<>();
        nom.setMinWidth(80);
        nom.setCellValueFactory(new PropertyValueFactory<>("Nom"));
        nom.setText("Nom");

        TableColumn <Classes, String> prenom = new TableColumn<>();
        prenom.setMinWidth(90);
        prenom.setCellValueFactory(new PropertyValueFactory<>("Prenom"));
        prenom.setText("Prenom");

        TableColumn <Classes, String> telephone = new TableColumn<>();
        telephone.setMinWidth(120);
        telephone.setCellValueFactory(new PropertyValueFactory<>("Telephone"));
        telephone.setText("Telephone");

        TableColumn <Classes, Integer> specialite = new TableColumn<>();
        specialite.setMinWidth(50);
        specialite.setCellValueFactory(new PropertyValueFactory<>("specialite"));
        specialite.setText("Specialité");

        TableColumn <Classes, Integer> email = new TableColumn<>();
        email.setMinWidth(150);
        email.setCellValueFactory(new PropertyValueFactory<>("Email"));
        email.setText("Email");

            //Affichage du tableau
        tableView.setItems(getEtudiant());
        tableView.getColumns().addAll(id, nom, prenom, telephone, email,specialite);
        centerVBox.getChildren().add(tableView);





            //Button Home
        GridPane homeBox = new GridPane();
        centerVBox.getChildren().add(homeBox);
        homeBox.setAlignment(Pos.CENTER);
        homeBox.setPadding(new Insets(30,0,0,0));

        Image homeIco = new Image(getClass().getResource("img/home.png").toString());
        ImageView iv = new ImageView();
        iv.setFitHeight(50);
        iv.setFitWidth(50);
        iv.setImage(homeIco);
        homeBox.getChildren().add(iv);

        Button back = new Button("Home");
        back.setMinHeight(50);
        back.setMinWidth(50);
        back.setOpacity(0);
        back.setCursor(Cursor.HAND);
        homeBox.getChildren().add(back);
        back.setOnAction(a->{
            back.getScene().getWindow().hide();
            Parent root = null;
            try {
                root = FXMLLoader.load(getClass().getResource("/sample/sample.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            Stage home = new Stage();
            home.setTitle("Gestion Ees Etudiants");
            Scene scene = new Scene(root);
            home.setScene(scene);
            home.show();

        });




    }

    public void addToList(){
        //Title
        centerVBox.getChildren().clear();
        title.setText("Ajouter un Etudiant");
        title.setTextFill(Color.WHITE);
        title.setFont(Font.font("Bodoni MT italic",36));
        title.setPadding(new Insets(0,0,30,0));
        centerVBox.getChildren().add(title);

        //

        //Les Inputs
        FlowPane inputs = new FlowPane(Orientation.VERTICAL);
        inputs.setAlignment(Pos.CENTER);
        inputs.setMinHeight(200);
        inputs.setMinWidth(50);
        centerVBox.getChildren().add(inputs);

        VBox nomVBox = new VBox();
        nomVBox.setPadding(new Insets(5,0,5,0));
        VBox prenomVBox = new VBox();
        prenomVBox.setPadding(new Insets(5,0,5,0));
        VBox telephoneVBox = new VBox();
        telephoneVBox.setPadding(new Insets(5,0,5,0));
        VBox emailVBox = new VBox();
        emailVBox.setPadding(new Insets(5,0,5,0));
        VBox specialiteVBox = new VBox();
        specialiteVBox.setPadding(new Insets(5,0,5,0));
        VBox btnVBox = new VBox();
        btnVBox.setPadding(new Insets(5,0,5,0));

        inputs.getChildren().add(nomVBox);
        inputs.getChildren().add(prenomVBox);
        inputs.getChildren().add(telephoneVBox);
        inputs.getChildren().add(emailVBox);
        inputs.getChildren().add(specialiteVBox);
        inputs.getChildren().add(btnVBox);

        TextField nomInput = new TextField();
        TextField prenomInput = new TextField();
        TextField telephoneInput = new TextField();
        TextField emailInput = new TextField();
        TextField specInput = new TextField();

        Label nomLabel = new Label("Nom:");
        nomLabel.setTextFill(Color.WHITE);
        Label prenomLabel = new Label("Prenom:");
        prenomLabel.setTextFill(Color.WHITE);
        Label telephoneLabel = new Label("Telephone:");
        telephoneLabel.setTextFill(Color.WHITE);
        Label emailLabel = new Label("Email:");
        emailLabel.setTextFill(Color.WHITE);
        Label specLabel = new Label("Specialite:");
        specLabel.setTextFill(Color.WHITE);

        nomVBox.getChildren().add(nomLabel);
        prenomVBox.getChildren().add(prenomLabel);
        telephoneVBox.getChildren().add(telephoneLabel);
        emailVBox.getChildren().add(emailLabel);
        specialiteVBox.getChildren().add(specLabel);

        nomVBox.getChildren().add(nomInput);
        prenomVBox.getChildren().add(prenomInput);
        telephoneVBox.getChildren().add(telephoneInput);
        emailVBox.getChildren().add(emailInput);
        specialiteVBox.getChildren().add(specInput);


        Button addBtn = new Button("Ajouter");
        btnVBox.getChildren().add(addBtn);
        btnVBox.setAlignment(Pos.CENTER);
        addBtn.setCursor(Cursor.HAND);
        addBtn.setAlignment(Pos.CENTER);



        //Ajout au base de donnée

        addBtn.setOnAction(a2->{
            Connection  cnx =ConnexionMySQL.cnx();
            PreparedStatement prepared;
            String prenom = prenomInput.getText().toString();
            String nom = nomInput.getText().toString();
            String tel = telephoneInput.getText().toString();
            String email = emailInput.getText().toString();
            String spacialite = specInput.getText().toString();
            String sql="INSERT INTO `etudiants`(`nom`, `prenom`, `tel`, `email`, `specialite`) VALUES(?,?,?,?,?)";
            try {
                prepared=cnx.prepareStatement(sql);
                prepared.setString(1,nom);
                prepared.setString(2,prenom);
                prepared.setString(3,tel);
                prepared.setString(4,email);
                prepared.setString(5,spacialite);
                prepared.execute();

                prenomInput.setText("");
                nomInput.setText("");
                telephoneInput.setText("");
                emailInput.setText("");
                specInput.setText("");
                afficherList();


            } catch (SQLException e1) {
                e1.printStackTrace();
            }


        });








        //Button Home
        GridPane homeBox = new GridPane();
        centerVBox.getChildren().add(homeBox);
        homeBox.setAlignment(Pos.CENTER);
        homeBox.setPadding(new Insets(30,0,0,0));

        Image homeIco = new Image(getClass().getResource("img/home.png").toString());
        ImageView iv = new ImageView();
        iv.setFitHeight(50);
        iv.setFitWidth(50);
        iv.setImage(homeIco);
        homeBox.getChildren().add(iv);

        Button back = new Button("Home");
        back.setMinHeight(50);
        back.setMinWidth(50);
        back.setOpacity(0);
        back.setCursor(Cursor.HAND);
        homeBox.getChildren().add(back);
        back.setOnAction(a->{
            back.getScene().getWindow().hide();
            Parent root = null;
            try {
                root = FXMLLoader.load(getClass().getResource("/sample/sample.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            Stage home = new Stage();
            home.setTitle("Gestion des Ã©tudiants");
            Scene scene = new Scene(root);
            home.setScene(scene);
            home.show();

        });

    }

    public void findInList(){
        centerVBox.getChildren().clear();
        leftVBox.getChildren().clear();
        title.setText("Rechercher un Etudiant");
        title.setTextFill(Color.WHITE);
        title.setFont(Font.font("Bodoni MT italic",36));
        title.setPadding(new Insets(0,0,30,0));
        centerVBox.getChildren().add(title);

    //Inputs
        HBox inputsHbox = new HBox();
        inputsHbox.setAlignment(Pos.BOTTOM_LEFT);
        inputsHbox.setPadding(new Insets(5,0,30,5));
        centerVBox.getChildren().add(inputsHbox);

        //instanciation des VBox
        VBox idVBox = new VBox();
        idVBox.setPadding(new Insets(5,20,5,5));
        inputsHbox.getChildren().add(idVBox);
        VBox nomVBox = new VBox();
        nomVBox.setPadding(new Insets(5,20,5,5));
        inputsHbox.getChildren().add(nomVBox);
        VBox prenomVBox = new VBox();
        prenomVBox.setPadding(new Insets(5,20,5,5));
        inputsHbox.getChildren().add(prenomVBox);
        VBox btnVBox = new VBox();
        btnVBox.setPadding(new Insets(5,20,-10,5));
        inputsHbox.getChildren().add(btnVBox);
        
        VBox leftInputs = new VBox();
        leftInputs.setPadding(new Insets(5,20,-10,5));
        leftVBox.getChildren().add(leftInputs);


        VBox nomVBoxLeft = new VBox();
        nomVBoxLeft.setPadding(new Insets(5,20,5,5));
        leftInputs.getChildren().add(nomVBoxLeft);
        VBox prenomVBoxLeft = new VBox();
        prenomVBoxLeft.setPadding(new Insets(5,20,5,5));
        leftInputs.getChildren().add(prenomVBoxLeft);
        VBox telVBoxLeft = new VBox();
        telVBoxLeft.setPadding(new Insets(5,20,5,5));
        leftInputs.getChildren().add(telVBoxLeft);
        VBox emailVBoxLeft = new VBox();
        emailVBoxLeft.setPadding(new Insets(5,20,5,5));
        leftInputs.getChildren().add(emailVBoxLeft);
        VBox specialiteVBoxLeft = new VBox();
        specialiteVBoxLeft.setPadding(new Insets(5,20,5,5));
        leftInputs.getChildren().add(specialiteVBoxLeft);
        VBox btnVBoxLeft = new VBox();
        btnVBoxLeft.setPadding(new Insets(5,20,-10,5));
        leftInputs.getChildren().add(btnVBoxLeft);

        //instanciation des labels
        Label idLabel = new Label("ID:");
        idLabel.setTextFill(Color.WHITE);
        Label nomLabel = new Label("Nom:");
        nomLabel.setTextFill(Color.WHITE);
        Label prenomLabel = new Label("Prenom:");
        prenomLabel.setTextFill(Color.WHITE);

        //instanciation des inputs
        TextField idInput = new TextField();
        TextField nomInput = new TextField();
        TextField prenomInput = new TextField();

        //afficher les labels
        idVBox.getChildren().add(idLabel);
        nomVBox.getChildren().add(nomLabel);
        prenomVBox.getChildren().add(prenomLabel);

        //afficher les inputs
        idVBox.getChildren().add(idInput);
        nomVBox.getChildren().add(nomInput);
        prenomVBox.getChildren().add(prenomInput);

        //inputsButton
        Button findBtn = new Button("Rechercher");
        btnVBox.getChildren().add(findBtn);
        btnVBox.setAlignment(Pos.CENTER);
        findBtn.setCursor(Cursor.HAND);
        findBtn.setAlignment(Pos.CENTER);

        //Left VBox
        TextField leftSpecInput = new TextField();
        TextField leftNomInput = new TextField();
        TextField leftPrenomInput = new TextField();
        TextField leftTelInput = new TextField();
        TextField leftEmailInput = new TextField();
        //left labels
        Label leftSpecLabel = new Label("Specialite:");
        leftSpecLabel.setTextFill(Color.WHITE);
        Label leftNomLabel = new Label("Nom:");
        leftNomLabel.setTextFill(Color.WHITE);
        Label leftPrenomLabel = new Label("Prenom:");
        leftPrenomLabel.setTextFill(Color.WHITE);
        Label leftTelLabel = new Label("Telephone:");
        leftTelLabel.setTextFill(Color.WHITE);
        Label leftEmailLabel = new Label("Email:");
        leftEmailLabel.setTextFill(Color.WHITE);

        //Left afficher les labels
        nomVBoxLeft.getChildren().add(leftNomLabel);
        prenomVBoxLeft.getChildren().add(leftPrenomLabel);
        telVBoxLeft.getChildren().add(leftTelLabel);
        emailVBoxLeft.getChildren().add(leftEmailLabel);
        specialiteVBoxLeft.getChildren().add(leftSpecLabel);
        //Left afficher les inputs
        nomVBoxLeft.getChildren().add(leftNomInput);
        prenomVBoxLeft.getChildren().add(leftPrenomInput);
        telVBoxLeft.getChildren().add(leftTelInput);
        emailVBoxLeft.getChildren().add(leftEmailInput);
        specialiteVBoxLeft.getChildren().add(leftSpecInput);
        //Left inputsButton
        Button updateBtn = new Button("Modifier");
        btnVBoxLeft.getChildren().add(updateBtn);
        btnVBoxLeft.setAlignment(Pos.CENTER);
        updateBtn.setCursor(Cursor.HAND);
        updateBtn.setAlignment(Pos.CENTER);

        leftVBox.getChildren().clear();
        Label leftTitle = new Label("Modifier");
        leftTitle.setTextFill(Color.WHITE);
        leftTitle.setFont(Font.font("Bodoni MT italic",24));
        leftTitle.setPadding(new Insets(0,0,0,30));
        
        leftVBox.getChildren().add(leftTitle);
        leftVBox.getChildren().add(leftInputs);
        


    //Tableau
        VBox tableauVBox = new VBox();
        centerVBox.getChildren().add(tableauVBox);
        ScrollPane tableBox = new ScrollPane();
        TableView <Classes> tableView = new TableView();
        tableView.setMaxHeight(150);

        //les colones du tableau
        TableColumn <Classes, Integer> id = new TableColumn<>();
        id.setMinWidth(38);
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        id.setText("ID");

        TableColumn <Classes, String> nom = new TableColumn<>();
        nom.setMinWidth(80);
        nom.setCellValueFactory(new PropertyValueFactory<>("Nom"));
        nom.setText("Nom");

        TableColumn <Classes, String> prenom = new TableColumn<>();
        prenom.setMinWidth(100);
        prenom.setCellValueFactory(new PropertyValueFactory<>("Prenom"));
        prenom.setText("Prenom");

        TableColumn <Classes, String> telephone = new TableColumn<>();
        telephone.setMinWidth(120);
        telephone.setCellValueFactory(new PropertyValueFactory<>("Telephone"));
        telephone.setText("Telephone");

        TableColumn <Classes, Integer> specialite = new TableColumn<>();
        specialite.setMinWidth(50);
        specialite.setCellValueFactory(new PropertyValueFactory<>("specialite"));
        specialite.setText("Specialite");

        TableColumn <Classes, Integer> email = new TableColumn<>();
        email.setMinWidth(150);
        email.setCellValueFactory(new PropertyValueFactory<>("Email"));
        email.setText("Email");

        //Affichage du tableau

        tableView.getColumns().addAll(id, nom, prenom, telephone, specialite, email);
        tableauVBox.getChildren().add(tableView);

        //recherche
        findBtn.setOnAction(a3->{
            int idFind = Integer.parseInt(idInput.getText());
            String prenomFind = prenomInput.getText().toString();
            String nomFind = nomInput.getText().toString();
            //Find Students Contenu
//            public ObservableList<Classes> findEtudiant () {
            ObservableList<Classes> etudiantListe = FXCollections.observableArrayList();
            //Connexion
            Connection  cnx =ConnexionMySQL.cnx();
            PreparedStatement prepared;
            String query = "SELECT * FROM etudiants WHERE id ="+idFind;
            Statement st;
            ResultSet rs;

            try{
                st = cnx.createStatement();
                rs = st.executeQuery(query);
                Classes etudiant;
                while(rs.next()){
                    etudiant = new Classes(rs.getInt("id"), rs.getString("nom"),
                            rs.getString("prenom"), rs.getString("tel"),
                            rs.getString("email"),rs.getString("specialite"));
                    etudiantListe.add(etudiant);

                }

            }catch(Exception ex){
                ex.printStackTrace();
            }


            tableView.setItems(etudiantListe);


        });

        tableView.setOnMouseClicked(a4->{
            leftNomInput.setText(tableView.getSelectionModel().getSelectedItem().getNom());
            leftPrenomInput.setText(tableView.getSelectionModel().getSelectedItem().getPrenom());
            leftTelInput.setText(tableView.getSelectionModel().getSelectedItem().getTelephone());
            leftEmailInput.setText(tableView.getSelectionModel().getSelectedItem().getEmail());
            leftSpecInput.setText(tableView.getSelectionModel().getSelectedItem().getSpecialite());
        });
        //Update
        updateBtn.setOnAction(a5->{
//            UPDATE `etudiants` SET `id`=[value-1],`nom`=[value-2],`prenom`=[value-3],`tel`=[value-4],`email`=[value-5],`specialite`=[value-6] WHERE 1
            Connection  cnx =ConnexionMySQL.cnx();
            PreparedStatement prepared;
            int idUpdate = tableView.getSelectionModel().getSelectedItem().getId();
            String prenomUpdate = leftPrenomInput.getText().toString();
            String nomUpdate = leftNomInput.getText().toString();
            String telUpdate = leftTelInput.getText().toString();
            String emailUpdate = leftEmailInput.getText().toString();
            String spacialiteUpdate = leftSpecInput.getText().toString();
            String sql="UPDATE `etudiants`SET `nom`=?,`prenom`=?,`tel`=?,`email`=?,`specialite`=? WHERE id="+idUpdate;
            try {
                prepared=cnx.prepareStatement(sql);
                prepared.setString(1,nomUpdate);
                prepared.setString(2,prenomUpdate);
                prepared.setString(3,telUpdate);
                prepared.setString(4,emailUpdate);
                prepared.setString(5,spacialiteUpdate);
                prepared.execute();

                leftPrenomInput.setText("");
                leftNomInput.setText("");
                leftTelInput.setText("");
                leftEmailInput.setText("");
                leftSpecInput.setText("");



            } catch (SQLException e1) {
                e1.printStackTrace();
            }

        });





        //Button Home
        GridPane homeBox = new GridPane();
        centerVBox.getChildren().add(homeBox);
        homeBox.setAlignment(Pos.CENTER);
        homeBox.setPadding(new Insets(30,0,0,0));

        Image homeIco = new Image(getClass().getResource("img/home.png").toString());
        ImageView iv = new ImageView();
        iv.setFitHeight(50);
        iv.setFitWidth(50);
        iv.setImage(homeIco);
        homeBox.getChildren().add(iv);

        Button back = new Button("Home");
        back.setMinHeight(50);
        back.setMinWidth(50);
        back.setOpacity(0);
        back.setCursor(Cursor.HAND);
        homeBox.getChildren().add(back);
        back.setOnAction(a->{
            back.getScene().getWindow().hide();
            Parent root = null;
            try {
                root = FXMLLoader.load(getClass().getResource("/sample/sample.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            Stage home = new Stage();
            home.setTitle("Gestion des Ã©tudiants");
            Scene scene = new Scene(root);
            home.setScene(scene);
            home.show();

        });
    }

    public void removeFromList(){
        centerVBox.getChildren().clear();
        title.setText("Liste Des Modules");
        title.setTextFill(Color.WHITE);
        title.setFont(Font.font("Bodoni MT italic",36));
        title.setPadding(new Insets(0,0,30,0));
        centerVBox.getChildren().add(title);



        //Button Home
        GridPane homeBox = new GridPane();
        centerVBox.getChildren().add(homeBox);
        homeBox.setAlignment(Pos.CENTER);
        homeBox.setPadding(new Insets(30,0,0,0));

        Image homeIco = new Image(getClass().getResource("img/home.png").toString());
        ImageView iv = new ImageView();
        iv.setFitHeight(50);
        iv.setFitWidth(50);
        iv.setImage(homeIco);
        homeBox.getChildren().add(iv);

        Button back = new Button("Home");
        back.setMinHeight(50);
        back.setMinWidth(50);
        back.setOpacity(0);
        back.setCursor(Cursor.HAND);
        homeBox.getChildren().add(back);
        back.setOnAction(a->{
            back.getScene().getWindow().hide();
            Parent root = null;
            try {
                root = FXMLLoader.load(getClass().getResource("/sample/sample.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            Stage home = new Stage();
            home.setTitle("Gestion des Ã©tudiants");
            Scene scene = new Scene(root);
            home.setScene(scene);
            home.show();

        });

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
