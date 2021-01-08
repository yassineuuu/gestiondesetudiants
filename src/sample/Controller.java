package sample;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
import java.sql.*;

public class Controller {

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
                        rs.getString("prénom"), rs.getString("tel"),
                        rs.getString("email"));
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
        ScrollPane tableBox = new ScrollPane();
        TableView <Classes> tableView = new TableView<Classes>();
        tableView.setMaxHeight(300);
            //les colones du tableau
        TableColumn <Classes, Integer> id = new TableColumn<>();
        id.setMinWidth(38);
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        id.setText("ID");

        TableColumn <Classes, String> nom = new TableColumn<>();
        nom.setMinWidth(100);
        nom.setCellValueFactory(new PropertyValueFactory<>("Nom"));
        nom.setText("Nom");

        TableColumn <Classes, String> prenom = new TableColumn<>();
        prenom.setMinWidth(100);
        prenom.setCellValueFactory(new PropertyValueFactory<>("Prenom"));
        prenom.setText("Prénom");

        TableColumn <Classes, String> telephone = new TableColumn<>();
        telephone.setMinWidth(150);
        telephone.setCellValueFactory(new PropertyValueFactory<>("Telephone"));
        telephone.setText("Téléphone");

        TableColumn <Classes, Integer> email = new TableColumn<>();
        email.setMinWidth(250);
        email.setCellValueFactory(new PropertyValueFactory<>("Email"));
        email.setText("Email");

            //Affichage du tableau
        tableView.setItems(getEtudiant());
        tableView.getColumns().addAll(id, nom, prenom, telephone, email);
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
            home.setTitle("Gestion des Ã©tudiants");
            Scene scene = new Scene(root);
            home.setScene(scene);
            home.show();

        });




    }

    public void addToList(){
        //Title
        centerVBox.getChildren().clear();
        title.setText("Ajouter un étudiant");
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
        VBox btnVBox = new VBox();
        btnVBox.setPadding(new Insets(5,0,5,0));

        inputs.getChildren().add(nomVBox);
        inputs.getChildren().add(prenomVBox);
        inputs.getChildren().add(telephoneVBox);
        inputs.getChildren().add(emailVBox);
        inputs.getChildren().add(btnVBox);

        TextField nomInput = new TextField();
        TextField prenomInput = new TextField();
        TextField telephoneInput = new TextField();
        TextField emailInput = new TextField();

        Label nomLabel = new Label("Nom:");
        nomLabel.setTextFill(Color.WHITE);
        Label prenomLabel = new Label("Prénom:");
        prenomLabel.setTextFill(Color.WHITE);
        Label telephoneLabel = new Label("Téléphone:");
        telephoneLabel.setTextFill(Color.WHITE);
        Label emailLabel = new Label("Email:");
        emailLabel.setTextFill(Color.WHITE);

        nomVBox.getChildren().add(nomLabel);
        prenomVBox.getChildren().add(prenomLabel);
        telephoneVBox.getChildren().add(telephoneLabel);
        emailVBox.getChildren().add(emailLabel);

        nomVBox.getChildren().add(nomInput);
        prenomVBox.getChildren().add(prenomInput);
        telephoneVBox.getChildren().add(telephoneInput);
        emailVBox.getChildren().add(emailInput);

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
            String sql="INSERT INTO `etudiants`(`nom`, `prénom`, `tel`, `email`) VALUES(?,?,?,?)";
            try {
                prepared=cnx.prepareStatement(sql);
                prepared.setString(1,prenom);
                prepared.setString(2,nom);
                prepared.setString(3,tel);
                prepared.setString(4,email);
                prepared.execute();

                prenomInput.setText("");
                nomInput.setText("");
                telephoneInput.setText("");
                emailInput.setText("");


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
        title.setText("Rechercher un Ã©tudiant");
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

        //instanciation des labels
        Label idLabel = new Label("ID:");
        idLabel.setTextFill(Color.WHITE);
        Label nomLabel = new Label("Nom:");
        nomLabel.setTextFill(Color.WHITE);
        Label prenomLabel = new Label("PrÃ©nom:");
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
        findBtn.setOnAction(a3->{
            String idFind = idInput.getText().toString();
            String prenomFind = prenomInput.getText().toString();
            String nomFind = nomInput.getText().toString();

        });


    //Tableau
        VBox tableauVBox = new VBox();
        centerVBox.getChildren().add(tableauVBox);
        ScrollPane tableBox = new ScrollPane();
        TableView <Classes> tableView = new TableView();
        tableView.setMaxHeight(70);

        //les colones du tableau
        TableColumn <Classes, Integer> id = new TableColumn<>();
        id.setMinWidth(38);
//        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        id.setText("ID");

        TableColumn <Classes, String> nom = new TableColumn<>();
        nom.setMinWidth(100);
//        nom.setCellValueFactory(new PropertyValueFactory<>("Nom"));
        nom.setText("Nom");

        TableColumn <Classes, String> prenom = new TableColumn<>();
        prenom.setMinWidth(100);
//        prenom.setCellValueFactory(new PropertyValueFactory<>("Prenom"));
        prenom.setText("PrÃ©nom");

        TableColumn <Classes, String> telephone = new TableColumn<>();
        telephone.setMinWidth(150);
//        telephone.setCellValueFactory(new PropertyValueFactory<>("Telephone"));
        telephone.setText("TÃ©lÃ©phone");

        TableColumn <Classes, Integer> email = new TableColumn<>();
        email.setMinWidth(250);
//        email.setCellValueFactory(new PropertyValueFactory<>("Email"));
        email.setText("Email");

        //Affichage du tableau
        tableView.setItems(getEtudiant());
        tableView.getColumns().addAll(id, nom, prenom, telephone, email);
        tableauVBox.getChildren().add(tableView);


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
}
