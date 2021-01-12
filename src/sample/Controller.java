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
import javafx.scene.Node;
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

public class Controller{

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
    private Button afficherModules;
    @FXML
    private Label leftTitle;
    @FXML
    private VBox leftVBox;
    @FXML
    private Label leftSpecLabel;

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

        TableColumn <Classes, String> specialite = new TableColumn<>();
        specialite.setMinWidth(50);
        specialite.setCellValueFactory(new PropertyValueFactory<>("specialite"));
        specialite.setText("Specialité");

        TableColumn <Classes, String> email = new TableColumn<>();
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
            Stage home = (Stage) ((Node)leftVBox).getScene().getWindow();
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
        ObservableList<String> options =
                FXCollections.observableArrayList(
                        "JEE",
                        "FEBE",
                        "C#"
                );
        ComboBox specInput = new ComboBox(options);

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
            String spacialite = specInput.getSelectionModel().getSelectedItem().toString();
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
                specInput.setValue("");
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
            Stage home = (Stage) ((Node)leftVBox).getScene().getWindow();
            Scene scene = new Scene(root);
            home.setScene(scene);
            home.show();

        });

    }

    public void findInList(){
        centerVBox.getChildren().clear();
        leftVBox.getChildren().clear();
        leftTitle.setText("Modifier");
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
//        VBox idVBox = new VBox();
//        idVBox.setPadding(new Insets(5,20,5,5));
//        inputsHbox.getChildren().add(idVBox);
//        VBox nomVBox = new VBox();
//        nomVBox.setPadding(new Insets(5,20,5,5));
//        inputsHbox.getChildren().add(nomVBox);
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
        btnVBoxLeft.setPadding(new Insets(5,40,-10,15));
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

        prenomVBox.getChildren().add(prenomLabel);

        //afficher les inputs

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
        Image editImg = new Image(getClass().getResource("img/edit.png").toString());
        ImageView updateBtn = new ImageView();
        updateBtn.setImage(editImg);
        updateBtn.setFitHeight(45);
        updateBtn.setFitWidth(45);
        btnVBoxLeft.getChildren().add(updateBtn);
        btnVBoxLeft.setAlignment(Pos.CENTER);
        updateBtn.setCursor(Cursor.HAND);
        btnVBoxLeft.getChildren().add(new Label());

        Image deleteImg = new Image(getClass().getResource("img/delete-forever.png").toString());
        ImageView deleteIV = new ImageView();
        deleteIV.setImage(deleteImg);
        deleteIV.setFitWidth(45);
        deleteIV.setFitHeight(45);
        btnVBoxLeft.getChildren().add(deleteIV);


        updateBtn.setOnMousePressed(e2->{
            updateBtn.setScaleX(0.9);
            updateBtn.setScaleY(0.9);
        });
        updateBtn.setOnMouseReleased(e3->{
            updateBtn.setScaleX(1);
            updateBtn.setScaleY(1);
        });
        deleteIV.setOnMousePressed(e2->{
            deleteIV.setScaleX(0.9);
            deleteIV.setScaleY(0.9);
        });
        deleteIV.setOnMouseReleased(e3->{
            deleteIV.setScaleX(1);
            deleteIV.setScaleY(1);
        });

        leftVBox.getChildren().clear();
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
//            int idFind = Integer.parseInt(idInput.getText());
            String prenomFind = prenomInput.getText().toString();
//            String nomFind = nomInput.getText().toString();
            System.out.println(prenomFind);

            //Find Students Contenu
            ObservableList<Classes> etudiantListe = FXCollections.observableArrayList();
            //Connexion
            Connection  cnx =ConnexionMySQL.cnx();
            PreparedStatement prepared;
            String query = "SELECT * FROM etudiants WHERE prenom = '" + prenomFind+"'";
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
        updateBtn.setOnMouseClicked(a5->{
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

        //DELETE
        deleteIV.setOnMouseClicked(e1->{
            System.out.println("DELETED");
            Connection  cnx =ConnexionMySQL.cnx();
            PreparedStatement prepared;
            int idDelete = tableView.getSelectionModel().getSelectedItem().getId();
            String sql = "DELETE FROM `etudiants` WHERE id="+idDelete;

            try {
                prepared = cnx.prepareStatement(sql);
                prepared.execute();
                leftPrenomInput.setText("");
                leftNomInput.setText("");
                leftTelInput.setText("");
                leftEmailInput.setText("");
                leftSpecInput.setText("");

                afficherList();
            }catch (SQLException e2){
                e2.printStackTrace();
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
            Stage home = (Stage) ((Node)leftVBox).getScene().getWindow();
            Scene scene = new Scene(root);
            home.setScene(scene);
            home.show();


        });
    }

    //LES Modules


    //get Modules (Contenue du tableau)
    public ObservableList<Modules> getModules(){
        ObservableList<Modules> moduleList = FXCollections.observableArrayList();
        //Connexion
        Connection  cnx =ConnexionMySQL.cnx();
        PreparedStatement prepared;
        String query = "SELECT * FROM modules";

        Statement st;
        ResultSet rs;

        try{
            st = cnx.createStatement();
            rs = st.executeQuery(query);
            Modules Module;
            while(rs.next()){
                Module = new Modules(rs.getInt("id"), rs.getString("specialite"), rs.getString("module1"), rs.getString("module2"), rs.getString("module3"),rs.getString("module4"),rs.getString("module5"), rs.getString("module6"), rs.getString("module7"),rs.getString("module8"), rs.getString("module9"), rs.getString("module10"));
                moduleList.add(Module);
            }

        }catch(Exception ex){
            ex.printStackTrace();
        }
        return moduleList;
    }
    public void afficherModules(){
        centerVBox.getChildren().clear();
        leftVBox.getChildren().clear();
        title.setText("Liste Des Modules");
        title.setTextFill(Color.WHITE);
        title.setFont(Font.font("Bodoni MT italic",36));
        title.setPadding(new Insets(0,0,30,0));
        centerVBox.getChildren().add(title);

//Tableau
        TableView <Modules> tv = new TableView<Modules>();
        tv.setMaxHeight(300);
        //les colones du tableau
        TableColumn <Modules, Integer> id = new TableColumn<>();
        id.setMinWidth(38);
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        id.setText("ID");

        TableColumn <Modules, String> Specialite = new TableColumn<>();
        Specialite.setMinWidth(80);
        Specialite.setCellValueFactory(new PropertyValueFactory<>("Specialite"));
        Specialite.setText("Specialite");

        TableColumn <Modules, String> module1 = new TableColumn<>();
        module1.setMinWidth(90);
        module1.setCellValueFactory(new PropertyValueFactory<>("Modules1"));
        module1.setText("Module1");

        TableColumn <Modules, String> module2 = new TableColumn<>();
        module2.setMinWidth(120);
        module2.setCellValueFactory(new PropertyValueFactory<>("Modules2"));
        module2.setText("Module2");

        TableColumn <Modules, String> module3 = new TableColumn<>();
        module3.setMinWidth(50);
        module3.setCellValueFactory(new PropertyValueFactory<>("Modules3"));
        module3.setText("Module3");

        TableColumn <Modules, String> module4 = new TableColumn<>();
        module4.setMinWidth(150);
        module4.setCellValueFactory(new PropertyValueFactory<>("Modules4"));
        module4.setText("Module4");

        TableColumn <Modules, String> module5 = new TableColumn<>();
        module5.setMinWidth(150);
        module5.setCellValueFactory(new PropertyValueFactory<>("Modules5"));
        module5.setText("Module5");

        TableColumn <Modules, String> module6 = new TableColumn<>();
        module6.setMinWidth(150);
        module6.setCellValueFactory(new PropertyValueFactory<>("Modules6"));
        module6.setText("Module6");

        TableColumn <Modules, String> module7 = new TableColumn<>();
        module7.setMinWidth(150);
        module7.setCellValueFactory(new PropertyValueFactory<>("Modules7"));
        module7.setText("Module7");

        TableColumn <Modules, String> module8 = new TableColumn<>();
        module8.setMinWidth(150);
        module8.setCellValueFactory(new PropertyValueFactory<>("Modules8"));
        module8.setText("Module8");

        TableColumn <Modules, String> module9 = new TableColumn<>();
        module9.setMinWidth(150);
        module9.setCellValueFactory(new PropertyValueFactory<>("Modules9"));
        module9.setText("Module9");

        TableColumn <Modules, String> module10 = new TableColumn<>();
        module10.setMinWidth(150);
        module10.setCellValueFactory(new PropertyValueFactory<>("Modules10"));
        module10.setText("Module10");

        //Affichage du tableau modules
        tv.setItems(getModules());
        tv.getColumns().addAll(id, Specialite, module1, module2, module3, module4, module5,module6, module7, module8, module9, module10);
        System.out.println(Specialite);
        centerVBox.getChildren().add(tv);


        //L'AJOUT des modules
        VBox leftInputs = new VBox();
        leftInputs.setPadding(new Insets(5,20,-10,5));
        leftVBox.getChildren().add(leftInputs);

        VBox specVBoxLeft = new VBox();
        specVBoxLeft.setPadding(new Insets(5,20,5,5));
        leftInputs.getChildren().add(specVBoxLeft);
        VBox m1VBoxLeft = new VBox();
        m1VBoxLeft.setPadding(new Insets(5,20,5,5));
        leftInputs.getChildren().add(m1VBoxLeft);
        VBox m2VBoxLeft = new VBox();
        m2VBoxLeft.setPadding(new Insets(5,20,5,5));
        leftInputs.getChildren().add(m2VBoxLeft);
        VBox m3VBoxLeft = new VBox();
        m3VBoxLeft.setPadding(new Insets(5,20,5,5));
        leftInputs.getChildren().add(m3VBoxLeft);
        VBox m4VBoxLeft = new VBox();
        m4VBoxLeft.setPadding(new Insets(5,20,5,5));
        leftInputs.getChildren().add(m4VBoxLeft);
        VBox m5VBoxLeft = new VBox();
        m5VBoxLeft.setPadding(new Insets(5,20,5,5));
        leftInputs.getChildren().add(m5VBoxLeft);
        VBox m6VBoxLeft = new VBox();
        m6VBoxLeft.setPadding(new Insets(5,20,5,5));
        leftInputs.getChildren().add(m6VBoxLeft);
        VBox m7VBoxLeft = new VBox();
        m7VBoxLeft.setPadding(new Insets(5,20,5,5));
        leftInputs.getChildren().add(m7VBoxLeft);
        VBox m8VBoxLeft = new VBox();
        m8VBoxLeft.setPadding(new Insets(5,20,5,5));
        leftInputs.getChildren().add(m8VBoxLeft);
        VBox m9VBoxLeft = new VBox();
        m9VBoxLeft.setPadding(new Insets(5,20,5,5));
        leftInputs.getChildren().add(m9VBoxLeft);
        VBox m10VBoxLeft = new VBox();
        m10VBoxLeft.setPadding(new Insets(5,20,5,5));
        leftInputs.getChildren().add(m10VBoxLeft);
        VBox btnVBoxLeft = new VBox();
        btnVBoxLeft.setPadding(new Insets(5,40,-10,15));
        leftInputs.getChildren().add(btnVBoxLeft);


        //Modules Ajout inputs
        ObservableList<String> options =
                FXCollections.observableArrayList(
                        "JEE",
                        "FEBE",
                        "C#"
                );
        ComboBox leftSpecInput = new ComboBox(options);
        TextField m1Input = new TextField();
        TextField m2Input = new TextField();
        TextField m3Input = new TextField();
        TextField m4Input = new TextField();
        TextField m5Input = new TextField();
        TextField m6Input = new TextField();
        TextField m7Input = new TextField();
        TextField m8Input = new TextField();
        TextField m9Input = new TextField();
        TextField m10Input = new TextField();

//        Modules Ajout labels
        Label leftSpecLabel = new Label("Specialite:");
        leftSpecLabel.setTextFill(Color.WHITE);
        Label leftModule1Label = new Label("Module1:");
        leftModule1Label.setTextFill(Color.WHITE);
        Label leftModule2Label = new Label("Module2:");
        leftModule2Label.setTextFill(Color.WHITE);
        Label leftModule3Label = new Label("Module3:");
        leftModule3Label.setTextFill(Color.WHITE);
        Label leftModule4Label = new Label("Module4:");
        leftModule4Label.setTextFill(Color.WHITE);
        Label leftModule5Label = new Label("Module5:");
        leftModule5Label.setTextFill(Color.WHITE);
        Label leftModule6Label = new Label("Module6:");
        leftModule6Label.setTextFill(Color.WHITE);
        Label leftModule7Label = new Label("Module7:");
        leftModule7Label.setTextFill(Color.WHITE);
        Label leftModule8Label = new Label("Module8:");
        leftModule8Label.setTextFill(Color.WHITE);
        Label leftModule9Label = new Label("Module9:");
        leftModule9Label.setTextFill(Color.WHITE);
        Label leftModule10Label = new Label("Module10:");
        leftModule10Label.setTextFill(Color.WHITE);


        //Left inputsButton
        Image addImg = new Image(getClass().getResource("img/add.png").toString());
        ImageView addIV = new ImageView();
        addIV.setCursor(Cursor.HAND);
        addIV.setImage(addImg);
        addIV.setFitWidth(45);
        addIV.setFitHeight(45);
        btnVBoxLeft.getChildren().add(addIV);
        btnVBoxLeft.getChildren().add(new Label());

        addIV.setOnMouseClicked(a7->{
            centerVBox.getChildren().clear();
            FlowPane ajtModule = new FlowPane(Orientation.VERTICAL);
            ajtModule.setMaxHeight(220);
            ajtModule.getChildren().add(specVBoxLeft);
            ajtModule.getChildren().add(m1VBoxLeft);
            ajtModule.getChildren().add(m2VBoxLeft);
            ajtModule.getChildren().add(m3VBoxLeft);
            ajtModule.getChildren().add(m4VBoxLeft);
            ajtModule.getChildren().add(m5VBoxLeft);
            ajtModule.getChildren().add(m6VBoxLeft);
            ajtModule.getChildren().add(m7VBoxLeft);
            ajtModule.getChildren().add(m8VBoxLeft);
            ajtModule.getChildren().add(m9VBoxLeft);
            ajtModule.getChildren().add(m10VBoxLeft);
            centerVBox.getChildren().add(ajtModule);

//      Left afficher les labels
        specVBoxLeft.getChildren().add(leftSpecLabel);
        m1VBoxLeft.getChildren().add(leftModule1Label);
        m2VBoxLeft.getChildren().add(leftModule2Label);
        m3VBoxLeft.getChildren().add(leftModule3Label);
        m4VBoxLeft.getChildren().add(leftModule4Label);
        m5VBoxLeft.getChildren().add(leftModule5Label);
        m6VBoxLeft.getChildren().add(leftModule6Label);
        m7VBoxLeft.getChildren().add(leftModule7Label);
        m8VBoxLeft.getChildren().add(leftModule8Label);
        m9VBoxLeft.getChildren().add(leftModule9Label);
        m10VBoxLeft.getChildren().add(leftModule10Label);
//        m5VBoxLeft.getChildren().add(leftModule4Label);
        //Left afficher les inputs
        specVBoxLeft.getChildren().add(leftSpecInput);
        m1VBoxLeft.getChildren().add(m1Input);
        m2VBoxLeft.getChildren().add(m2Input);
        m3VBoxLeft.getChildren().add(m3Input);
        m4VBoxLeft.getChildren().add(m4Input);
        m5VBoxLeft.getChildren().add(m5Input);
        m6VBoxLeft.getChildren().add(m6Input);
        m7VBoxLeft.getChildren().add(m7Input);
        m8VBoxLeft.getChildren().add(m8Input);
        m9VBoxLeft.getChildren().add(m9Input);
        m10VBoxLeft.getChildren().add(m10Input);

//        m5VBoxLeft.getChildren().add(leftSpecInput);

            VBox btnVBoxC = new VBox();
            btnVBoxC.setPadding(new Insets(5,40,-10,15));
            ajtModule.getChildren().add(btnVBoxC);
            Button addBtn = new Button("Ajouter");
            btnVBoxLeft.getChildren().add(addBtn);
            btnVBoxLeft.setAlignment(Pos.CENTER);
            addBtn.setCursor(Cursor.HAND);
            btnVBoxC.getChildren().add(addBtn);
            addBtn.setOnAction(a8->{
                Connection  cnx =ConnexionMySQL.cnx();
                PreparedStatement prepared;
                String spacialite = String.valueOf(leftSpecInput.getSelectionModel().getSelectedItem());
                String M1 = m1Input.getText().toString();
                String M2 = m2Input.getText().toString();
                String M3 = m3Input.getText().toString();
                String M4 = m4Input.getText().toString();
                String M5 = m5Input.getText().toString();
                String M6 = m6Input.getText().toString();
                String M7 = m7Input.getText().toString();
                String M8 = m8Input.getText().toString();
                String M9 = m9Input.getText().toString();
                String M10 = m10Input.getText().toString();
//                String spacialite = leftSpecInput.getSelectionModel().getSelectedItem().toString();
                String sql="INSERT INTO `modules`(`specialite`, `module1`, `module2`, `module3`, `module4`, `module5`, `module6`, `module7`, `module8`, `module9`, `module10`) VALUES(?,?,?,?,?,?,?,?,?,?,?)";
                try {
                    prepared=cnx.prepareStatement(sql);
                    prepared.setString(1,spacialite);
                    prepared.setString(2,M1);
                    prepared.setString(3,M2);
                    prepared.setString(4,M3);
                    prepared.setString(5,M4);
                    prepared.setString(6,M5);
                    prepared.setString(7,M6);
                    prepared.setString(8,M7);
                    prepared.setString(9,M8);
                    prepared.setString(10,M9);
                    prepared.setString(11,M10);
                    prepared.execute();


                    afficherModules();


                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            });

        });


        Image editImg = new Image(getClass().getResource("img/edit.png").toString());
        ImageView updateBtn = new ImageView();
        updateBtn.setImage(editImg);
        updateBtn.setFitHeight(45);
        updateBtn.setFitWidth(45);
        btnVBoxLeft.getChildren().add(updateBtn);
        btnVBoxLeft.setAlignment(Pos.CENTER);
        updateBtn.setCursor(Cursor.HAND);
        btnVBoxLeft.getChildren().add(new Label());
        updateBtn.setOnMouseClicked(e5->{
            centerVBox.getChildren().clear();
            FlowPane ajtModule = new FlowPane(Orientation.VERTICAL);
            ajtModule.setMaxHeight(220);
            ajtModule.getChildren().add(specVBoxLeft);
            ajtModule.getChildren().add(m1VBoxLeft);
            ajtModule.getChildren().add(m2VBoxLeft);
            ajtModule.getChildren().add(m3VBoxLeft);
            ajtModule.getChildren().add(m4VBoxLeft);
            ajtModule.getChildren().add(m5VBoxLeft);
            ajtModule.getChildren().add(m6VBoxLeft);
            ajtModule.getChildren().add(m7VBoxLeft);
            ajtModule.getChildren().add(m8VBoxLeft);
            ajtModule.getChildren().add(m9VBoxLeft);
            ajtModule.getChildren().add(m10VBoxLeft);
            centerVBox.getChildren().add(ajtModule);

//      Left afficher les labels
            specVBoxLeft.getChildren().add(leftSpecLabel);
            m1VBoxLeft.getChildren().add(leftModule1Label);
            m2VBoxLeft.getChildren().add(leftModule2Label);
            m3VBoxLeft.getChildren().add(leftModule3Label);
            m4VBoxLeft.getChildren().add(leftModule4Label);
            m5VBoxLeft.getChildren().add(leftModule5Label);
            m6VBoxLeft.getChildren().add(leftModule6Label);
            m7VBoxLeft.getChildren().add(leftModule7Label);
            m8VBoxLeft.getChildren().add(leftModule8Label);
            m9VBoxLeft.getChildren().add(leftModule9Label);
            m10VBoxLeft.getChildren().add(leftModule10Label);
//        m5VBoxLeft.getChildren().add(leftModule4Label);
            //Left afficher les inputs
            specVBoxLeft.getChildren().add(leftSpecInput);
            m1VBoxLeft.getChildren().add(m1Input);
            m2VBoxLeft.getChildren().add(m2Input);
            m3VBoxLeft.getChildren().add(m3Input);
            m4VBoxLeft.getChildren().add(m4Input);
            m5VBoxLeft.getChildren().add(m5Input);
            m6VBoxLeft.getChildren().add(m6Input);
            m7VBoxLeft.getChildren().add(m7Input);
            m8VBoxLeft.getChildren().add(m8Input);
            m9VBoxLeft.getChildren().add(m9Input);
            m10VBoxLeft.getChildren().add(m10Input);

//        m5VBoxLeft.getChildren().add(leftSpecInput);

            VBox btnVBoxC = new VBox();
            btnVBoxC.setPadding(new Insets(5,40,-10,15));
            ajtModule.getChildren().add(btnVBoxC);
            Button addBtn = new Button("Modifier");
            btnVBoxLeft.getChildren().add(addBtn);
            btnVBoxLeft.setAlignment(Pos.CENTER);
            addBtn.setCursor(Cursor.HAND);
            btnVBoxC.getChildren().add(addBtn);
            addBtn.setOnAction(e6->{

                Connection  cnx =ConnexionMySQL.cnx();
                PreparedStatement prepared;
                leftSpecInput.setValue(tv.getSelectionModel().getSelectedItem().getSpecialite().toString());
                m1Input.setText(tv.getSelectionModel().getSelectedItem().getModules1().toString());
                String spacialite = leftSpecInput.getSelectionModel().getSelectedItem().toString();
                String M1 = m1Input.getText().toString();
                String M2 = m2Input.getText().toString();
                String M3 = m3Input.getText().toString();
                String M4 = m4Input.getText().toString();
                String M5 = m5Input.getText().toString();
                String M6 = m6Input.getText().toString();
                String M7 = m7Input.getText().toString();
                String M8 = m8Input.getText().toString();
                String M9 = m9Input.getText().toString();
                String M10 = m10Input.getText().toString();
                int idUpdate = tv.getSelectionModel().getSelectedItem().getId();
                String sql="UPDATE `modules` SET `specialite`=?,`module1`=?,`module2`=?,`module3`=?,`module4`=?,`module5`=?,`module6`=?,`module7`=?,`module8`=?,`module9`=?,`module10`=? WHERE id ="+idUpdate;
                try {

                    prepared=cnx.prepareStatement(sql);
                    prepared.setString(1,spacialite);
                    prepared.setString(2,M1);
                    prepared.setString(3,M2);
                    prepared.setString(4,M3);
                    prepared.setString(5,M4);
                    prepared.setString(6,M5);
                    prepared.setString(7,M6);
                    prepared.setString(8,M7);
                    prepared.setString(9,M8);
                    prepared.setString(10,M9);
                    prepared.setString(11,M10);
                    prepared.execute();


                    afficherModules();


                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            });
        });

        Image deleteImg = new Image(getClass().getResource("img/delete-forever.png").toString());
        ImageView deleteIV = new ImageView();
        deleteIV.setCursor(Cursor.HAND);
        deleteIV.setImage(deleteImg);
        deleteIV.setFitWidth(45);
        deleteIV.setFitHeight(45);
        btnVBoxLeft.getChildren().add(deleteIV);
        deleteIV.setOnMouseClicked(a9->{
            Connection  cnx =ConnexionMySQL.cnx();
            PreparedStatement prepared;
            int idDelete = tv.getSelectionModel().getSelectedItem().getId();
            String sql = "DELETE FROM `modules` WHERE id="+idDelete;

            try {
                prepared = cnx.prepareStatement(sql);
                prepared.execute();

                afficherModules();
            }catch (SQLException e2){
                e2.printStackTrace();
            }
        });


        addIV.setOnMousePressed(e2->{
            addIV.setScaleX(0.9);
            addIV.setScaleY(0.9);
        });
        addIV.setOnMouseReleased(e3->{
            addIV.setScaleX(1);
            addIV.setScaleY(1);
        });

        updateBtn.setOnMousePressed(e2->{
            updateBtn.setScaleX(0.9);
            updateBtn.setScaleY(0.9);
        });
        updateBtn.setOnMouseReleased(e3->{
            updateBtn.setScaleX(1);
            updateBtn.setScaleY(1);
        });
        deleteIV.setOnMousePressed(e2->{
            deleteIV.setScaleX(0.9);
            deleteIV.setScaleY(0.9);
        });
        deleteIV.setOnMouseReleased(e3->{
            deleteIV.setScaleX(1);
            deleteIV.setScaleY(1);
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
            Stage home = (Stage) ((Node)leftVBox).getScene().getWindow();
            Scene scene = new Scene(root);
            home.setScene(scene);
            home.show();

        });

    }

    //get Spécialité
    public ObservableList<Specialite> getSpeciality(){
        ObservableList<Specialite> specList = FXCollections.observableArrayList();
        //Connexion
        Connection  cnx =ConnexionMySQL.cnx();
        PreparedStatement prepared;
        String query = "SELECT * FROM specialite";

        Statement st;
        ResultSet rs;

        try{
            st = cnx.createStatement();
            rs = st.executeQuery(query);
            Specialite speciality;
            while(rs.next()){
                speciality = new Specialite(rs.getInt("id"), rs.getString("nom"));
                specList.add(speciality);
            }

        }catch(Exception ex){
            ex.printStackTrace();
        }
        return specList;
    }
    //Afficher les specialites
    public void afficherSpecialite(){

    }


}
