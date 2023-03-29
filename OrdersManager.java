import java.sql.Driver;
import java.text.NumberFormat;
import java.util.Optional;

import javax.naming.spi.DirStateFactory.Result;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.binding.DoubleBinding;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.control.TableCell;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Pane;
import javafx.util.Callback;

/**
* GUI for Off-campus Housing application
*/
public class OrdersManager extends Application {
 
    // WIDTH and HEIGHT of GUI stored as constants 
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;

    private static final int MIN_WIDTH = 800;
    private static final int MIN_HEIGHT = 600;

    private final int ORDERS_COL_NUM = 6;
    private final int DRIVER_COL_NUM = 4; 
    private final int RESTAURANT_COL_NUM = 3; 

    // visual components for Orders in Progress Tab
    TableView ordersInProgressTable = new TableView<Order>();
    private Label ordersInProgressLabel = new Label("Orders In Progress");
    private TextField orderedByField =  new TextField();
    private ComboBox driverCombo =  new ComboBox();
    private ComboBox restaurantCombo =  new ComboBox();
    private Button addOrderButton = new Button("Add Order");
    private TextArea notificationArea1  = new TextArea();
    private Button quitButton1 = new Button("Quit");
    private ObservableList<Order> ordersInProgressList = FXCollections.observableArrayList(); 
    Label orderedByLabel = new Label("Ordered By");
    Label driverLabel = new Label("Driver"); 
    Label restaurantFieldLabel = new Label("Restaurant");

    // visual components for Orders Completed Tab
    TableView ordersCompletedTable = new TableView<Order>();
    private Label ordersCompletedLabel = new Label("Orders Completed");
    private Button quitButton2 = new Button("Quit");
    private ObservableList<Order> ordersCompletedList = FXCollections.observableArrayList();

    // visual components for Drivers Tab
    TableView<Deliverer> driversTable = new TableView<Deliverer>();
    private ObservableList<Deliverer> driverList = FXCollections.observableArrayList(new Deliverer("Bobbert", "Bobby"));
    private Label driversLabel = new Label("Drivers");
    private Label firstNameLabel = new Label("First Name");
    private Label lastNameLabel = new Label("Last Name");
    private TextField firstNameField =  new TextField();
    private TextField lastNameField =  new TextField();
    private Button addDriverButton = new Button("Add Driver");
    private TextArea notificationArea2  = new TextArea();
    private Button quitButton3 = new Button("Quit");

    //visual components for Restaurants Tab
    TableView<Restaurant> restaurantTable = new TableView<Restaurant>();
    private ObservableList<Restaurant> restaurantList = FXCollections.observableArrayList(new Restaurant("McDonald's"));
    private Label restauarantLabel = new Label("Restaurants");
    private Label restaurantNameLabel = new Label("Name");
    private TextField restaurantNameField =  new TextField();
    private Button addRestaurantButton = new Button("Add Restaurant");
    private TextArea notificationArea3  = new TextArea();
    private Button quitButton4 = new Button("Quit");

    // miscellaneous
    private Button saveButton  = new Button("Save Orders");
    private static final Background buttonBackground =
            new Background(new BackgroundFill(Color.LIGHTYELLOW, 
				new CornerRadii(10), Insets.EMPTY));
    DoubleBinding usedWidth;

    @Override
    /** Initialises the screen 
    *  @param stage:   The scene's stage 
    */
    public void start(Stage stage) {
        // Orders In Progress Setup
        buildComboBoxes();
        setOrderTableColumns(ordersInProgressTable);
        addButtonToOrderTable();
        ordersInProgressTable.setItems(ordersInProgressList);
        ordersInProgressLabel.setFont(new Font("Arial", 30));
        // create four HBoxes
        HBox orderDetails = new HBox (10); 

        notificationArea1.setEditable(false);

        // add components to Order Details (HBox)
        orderDetails.getChildren().addAll(orderedByLabel, orderedByField, 
                                            driverLabel, driverCombo, restaurantFieldLabel, restaurantCombo);
        
        // add components to main Vbox
        VBox ordersInProgressContent = new VBox(10);
        ordersInProgressContent.setPadding(new Insets(15));
        ordersInProgressContent.getChildren().addAll(ordersInProgressLabel, 
                          orderDetails, addOrderButton, ordersInProgressTable, notificationArea1,
                          quitButton1);
    
        // Make table resizeable
        ordersInProgressTable.prefHeightProperty().bind(stage.heightProperty());
        ordersInProgressTable.prefWidthProperty().bind(stage.widthProperty());
        notificationArea1.setMaxSize(WIDTH-80, HEIGHT/50);

        // Orders Completed Setup
        setOrderTableColumns(ordersCompletedTable);
        addTimeCompleteCol();
        ordersCompletedTable.setItems(ordersCompletedList);
        ordersCompletedLabel.setFont(new Font("Arial", 30));
        
        // add components to main Vbox
        VBox ordersCompletedContent = new VBox(10);
        ordersCompletedContent.setPadding(new Insets(15));
        ordersCompletedContent.getChildren().addAll(ordersCompletedLabel, 
                          ordersCompletedTable, quitButton2);
        
        // make tables resizeable
        ordersCompletedTable.prefHeightProperty().bind(stage.heightProperty());
        ordersCompletedTable.prefWidthProperty().bind(stage.widthProperty());
        
        // Driver Tab Setup
        setDriverTableColumns();
        driversTable.setItems(driverList);
        driversLabel.setFont(new Font("Arial", 30));
        // create four HBoxes
        HBox driverDetails = new HBox (10); 

        notificationArea2.setEditable(false);

        // add components to Order Details (HBox)
        driverDetails.getChildren().addAll(firstNameLabel, firstNameField, lastNameLabel, lastNameField);
        
        // // add components to main Vbox
        VBox driversContent = new VBox(10);
        driversContent.setPadding(new Insets(15));
        driversContent.getChildren().addAll(driversLabel, 
                          driverDetails, addDriverButton, driversTable, notificationArea2,
                          quitButton3);
    
        // // Make table resizeable
        driversTable.prefHeightProperty().bind(stage.heightProperty());
        driversTable.prefWidthProperty().bind(stage.widthProperty());
        notificationArea2.setMaxSize(WIDTH-80, HEIGHT/50);

        // Restauarant Tab Setup
        setRestaurantTableColumns();
        restaurantTable.setItems(restaurantList);
        restauarantLabel.setFont(new Font("Arial", 30));
        // create four HBoxes
        HBox restaurantDetails = new HBox (10); 

        notificationArea3.setEditable(false);

        // add components to Order Details (HBox)
        restaurantDetails.getChildren().addAll(restaurantNameLabel, restaurantNameField);
        
        // // add components to main Vbox
        VBox restaurantContent = new VBox(10);
        restaurantContent.setPadding(new Insets(15));
        restaurantContent.getChildren().addAll(restauarantLabel, 
                          restaurantDetails, addRestaurantButton, restaurantTable, notificationArea3,
                          quitButton4);
    
        // // Make table resizeable
        restaurantTable.prefHeightProperty().bind(stage.heightProperty());
        restaurantTable.prefWidthProperty().bind(stage.widthProperty());
        notificationArea3.setMaxSize(WIDTH-80, HEIGHT/50);

        // Other Stuff
        addRemoveButtonsToTables();

        stage.setWidth(WIDTH);
        stage.setHeight(HEIGHT);    
      
        stage.setMinWidth(MIN_WIDTH);
        stage.setMinHeight(MIN_HEIGHT);

        setHandlers();
        HBox[] hBoxes = {orderDetails, driverDetails, restaurantDetails};
        VBox[] vBoxes =  {ordersInProgressContent, ordersCompletedContent, driversContent, restaurantContent};
        setAlignments(hBoxes, vBoxes, Pos.CENTER);

        // Setting up TabPane
        TabPane tabPane = new TabPane();
        tabPane.setBackground(new Background(new BackgroundFill(Color.BEIGE, CornerRadii.EMPTY, Insets.EMPTY)));
        tabPane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
        Tab tab1 = new Tab();
        tab1.setText("Orders In Progress");
        tab1.setContent(ordersInProgressContent);
        Tab tab2 = new Tab();
        tab2.setText("Orders Completed");
        tab2.setContent(ordersCompletedContent);
        Tab tab3 = new Tab();
        tab3.setText("Drivers");
        tab3.setContent(driversContent);
        Tab tab4 = new Tab();
        tab4.setText("Restaurant");
        tab4.setContent(restaurantContent);
        tabPane.getTabs().addAll(tab1, tab2, tab3, tab4);

        // create the scene
        Scene scene = new Scene(tabPane);

        // configure the stage and make the stage visible
        stage.setScene(scene);
        stage.setTitle("Food Order Management");

        stage.show(); 
    }

    /**
    * Method to build Combo Boxes for the OrdersInProgress Driver and Restaurant Selection
    */
    private void buildComboBoxes() {
        buildDriverCombo();
        buildRestaurantCombo();
    }

    /**
    * Method to build the combo box from the Driver List
    */
    private void buildDriverCombo() {
        driverCombo.getItems().clear();
        for (Deliverer driver : driverList) {
            driverCombo.getItems().add(driver.getFull());
        }
        driverCombo.setValue(driverList.get(0).getFull());
    }

    /**
    * Method to build the combo box from the Restaurant List
    */
    private void buildRestaurantCombo() {
        restaurantCombo.getItems().clear();
        for (Restaurant restaurant : restaurantList) {
            restaurantCombo.getItems().add(restaurant.getName());
        }
        restaurantCombo.setValue(restaurantList.get(0).getName());
    }
       
    /**
    *  @param tView: The table whose columns are being built for 
    *  Method to build the table columns for the argument table (for the order tables)
    */
    private void setOrderTableColumns(TableView<Order> tView) {
        TableColumn<Order,String> firstCol = new TableColumn<Order,String>("Order ID");
        firstCol.setCellValueFactory(new PropertyValueFactory<Order,String>("id"));
        firstCol.prefWidthProperty().bind(tView.widthProperty().divide(ORDERS_COL_NUM));

        TableColumn<Order,String> secondCol = new TableColumn<Order,String>("Ordered by");
        secondCol.setCellValueFactory(new PropertyValueFactory<Order,String>("orderedby"));
        secondCol.prefWidthProperty().bind(tView.widthProperty().divide(ORDERS_COL_NUM));

        TableColumn<Order,String> thirdCol = new TableColumn<Order,String>("Driver");
        thirdCol.setCellValueFactory(new PropertyValueFactory<Order,String>("driver"));
        thirdCol.prefWidthProperty().bind(tView.widthProperty().divide(ORDERS_COL_NUM));

        TableColumn<Order,String> fourthCol = new TableColumn<Order,String>("Restaurant");
        fourthCol.setCellValueFactory(new PropertyValueFactory<Order,String>("restaurant"));
        fourthCol.prefWidthProperty().bind(tView.widthProperty().divide(ORDERS_COL_NUM));

        TableColumn<Order,String> fifthCol = new TableColumn<Order,String>("Time Placed");
        fifthCol.setCellValueFactory(new PropertyValueFactory<Order,String>("timeStart"));
        fifthCol.prefWidthProperty().bind(tView.widthProperty().divide(ORDERS_COL_NUM));

        tView.getColumns().addAll(firstCol, secondCol, thirdCol, fourthCol, fifthCol);
        usedWidth = firstCol.widthProperty().add(secondCol.widthProperty()).add(thirdCol.widthProperty()).add(fourthCol.widthProperty()).add(fifthCol.widthProperty());
    }

    /**
    *  Method to build the table columns for the Driver table
    */
    private void setDriverTableColumns() {
        TableColumn<Deliverer,String> firstCol = new TableColumn<Deliverer,String>("Driver ID");
        firstCol.setCellValueFactory(new PropertyValueFactory<Deliverer,String>("id"));
        firstCol.prefWidthProperty().bind(driversTable.widthProperty().divide(DRIVER_COL_NUM));

        TableColumn<Deliverer,String> secondCol = new TableColumn<Deliverer,String>("First Name");
        secondCol.setCellValueFactory(new PropertyValueFactory<Deliverer,String>("first"));
        secondCol.prefWidthProperty().bind(driversTable.widthProperty().divide(DRIVER_COL_NUM));

        TableColumn<Deliverer,String> thirdCol = new TableColumn<Deliverer,String>("Last Name");
        thirdCol.setCellValueFactory(new PropertyValueFactory<Deliverer,String>("last"));
        thirdCol.prefWidthProperty().bind(driversTable.widthProperty().divide(DRIVER_COL_NUM));

        driversTable.getColumns().addAll(firstCol, secondCol, thirdCol);
    }

    /**
    *  Method to build the table columns for the Restaurant table 
    */
    private void setRestaurantTableColumns() {
        TableColumn<Restaurant,String> firstCol = new TableColumn<Restaurant,String>("Restaurant ID");
        firstCol.setCellValueFactory(new PropertyValueFactory<Restaurant,String>("id"));
        firstCol.prefWidthProperty().bind(restaurantTable.widthProperty().divide(RESTAURANT_COL_NUM));

        TableColumn<Restaurant,String> secondCol = new TableColumn<Restaurant,String>("Name");
        secondCol.setCellValueFactory(new PropertyValueFactory<Restaurant,String>("name"));
        secondCol.prefWidthProperty().bind(restaurantTable.widthProperty().divide(RESTAURANT_COL_NUM));

        restaurantTable.getColumns().addAll(firstCol, secondCol);
    }

    /**
    *  Method to build the mark as completed table column for the OrdersInProgress table (for the order tables)
    */
    private void addButtonToOrderTable() {
        TableColumn<Order, Void> colBtn = new TableColumn("");
        colBtn.setStyle( "-fx-alignment: CENTER;");
        Callback<TableColumn<Order, Void>, TableCell<Order, Void>> cellFactory = new Callback<TableColumn<Order, Void>, TableCell<Order, Void>>() {
            @Override
            public TableCell<Order, Void> call(final TableColumn<Order, Void> param) {
                final TableCell<Order, Void> cell = new TableCell<Order, Void>() {
                    private final Button btn = new Button("Mark As Complete");
                    {
                        btn.setOnAction((ActionEvent e) -> {
                            Order t = getTableView().getItems().get(getIndex());
                            markAsCompletedHandler(t);
                        });
                    }
                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        };
        colBtn.setCellFactory(cellFactory);
        ordersInProgressTable.getColumns().add(colBtn);
        colBtn.prefWidthProperty().bind(ordersInProgressTable.widthProperty().subtract(usedWidth));
    }

    /** 
    *  Method to build the remove button table column for the driver and restaurant tables
    */
    private void addRemoveButtonsToTables() {
        TableColumn<Deliverer, Void> colBtn = new TableColumn("");
        colBtn.setStyle( "-fx-alignment: CENTER;");
        Callback<TableColumn<Deliverer, Void>, TableCell<Deliverer, Void>> cellFactory = new Callback<TableColumn<Deliverer, Void>, TableCell<Deliverer, Void>>() {
            @Override
            public TableCell<Deliverer, Void> call(final TableColumn<Deliverer, Void> param) {
                final TableCell<Deliverer, Void> cell = new TableCell<Deliverer, Void>() {
                    private final Button btn = new Button("Remove");
                    {
                        btn.setOnAction((ActionEvent e) -> {
                            Deliverer t = getTableView().getItems().get(getIndex());
                            removeDriverHandler(t);
                        });
                    }
                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        };
        colBtn.setCellFactory(cellFactory);
        driversTable.getColumns().add(colBtn);
        colBtn.prefWidthProperty().bind(driversTable.widthProperty().divide(DRIVER_COL_NUM));

        TableColumn<Restaurant, Void> colBtn2 = new TableColumn("");
        colBtn2.setStyle( "-fx-alignment: CENTER;");
        Callback<TableColumn<Restaurant, Void>, TableCell<Restaurant, Void>> cellFactory2 = new Callback<TableColumn<Restaurant, Void>, TableCell<Restaurant, Void>>() {
            @Override
            public TableCell<Restaurant, Void> call(final TableColumn<Restaurant, Void> param) {
                final TableCell<Restaurant, Void> cell = new TableCell<Restaurant, Void>() {
                    private final Button btn2 = new Button("Remove");
                    {
                        btn2.setOnAction((ActionEvent e) -> {
                            Restaurant t = getTableView().getItems().get(getIndex());
                            removeRestaurantHandler(t);
                        });
                    }
                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn2);
                        }
                    }
                };
                return cell;
            }
        };
        colBtn2.setCellFactory(cellFactory2);
        restaurantTable.getColumns().add(colBtn2);
        colBtn2.prefWidthProperty().bind(restaurantTable.widthProperty().divide(RESTAURANT_COL_NUM));
    }

    /**
    *  Method to build the time completed table column for the orders completed table
    */
    private void addTimeCompleteCol() {
        TableColumn<Order,String> timeCol = new TableColumn<Order,String>("Time Completed");
        timeCol.setCellValueFactory(new PropertyValueFactory<Order,String>("timeCompleted"));
        ordersCompletedTable.getColumns().add(timeCol);
        timeCol.prefWidthProperty().bind(ordersInProgressTable.widthProperty().divide(ORDERS_COL_NUM));
    }

    private void setAlignments(HBox[] hb, VBox[] vb, Pos pos) {

        for (int i=0; i<hb.length; i++) {
           hb[i].setAlignment(pos);
        }
        for (int i=0; i<vb.length; i++) {
            vb[i].setAlignment(pos);
         }
    }

    private int getNumberOfOrders() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setHeaderText("How many orders in the list?");
        dialog.setTitle("Order Information Request");
        
        Optional<String> result = dialog.showAndWait();
        return getValidPositiveIntegerOnOK(result);
    }

    /**
    * Method to return user positive int after pressing OK on a TextInputDialog
    * @return positive integer as parsed or 
    * close the application for bad input or Cancel pressed
    */
    private int getValidPositiveIntegerOnOK(Optional<String> result) {
        if (result.isPresent()) {
            String response = result.get();
            int num = getPositiveInteger(response);
            if (num != -1) return num;
        } 

        // Cancel Pressed or invalid int
        System.out.println("A positive integer should have been entered");
        System.out.println("Goodbye");
        Platform.exit();
        return -1; 
    }

    /**
    * Method to return a positive int from the String passed
    * @return positive integer or 
    * -1 for String that aren't positive int 
    */
    private int getPositiveInteger(String aString) {
        if (aString.matches("^[+]?\\d+$")) {
                return Integer.parseInt(aString);
        }
        return -1;
    }

    private void setHandlers() {

        addOrderButton.setOnAction( e -> addOrderHandler());
        addDriverButton.setOnAction( e -> addDriverHandler());
        addRestaurantButton.setOnAction( e -> addRestaurantHandler());
        quitButton1.setOnAction( e -> Platform.exit());
        quitButton2.setOnAction( e -> Platform.exit());
        quitButton3.setOnAction( e -> Platform.exit());
        quitButton4.setOnAction( e -> Platform.exit());
    }


    // event handler methods
    private void addOrderHandler() {

        String orderedBy =  orderedByField.getText().toString();

        String driver =  driverCombo.getValue().toString();
        String restaurant =  restaurantCombo.getValue().toString();
        // check for errors
        if ( orderedBy.length()== 0) {
            notificationArea1.setText("A name for ordered by must be entered");
        } 
        else { // ok to add the Order

            Order t =  new Order(orderedBy, driver, restaurant);
 
            ordersInProgressList.add(t); 

            notificationArea1.setText("Order # " 	
                   +  t.getId() +  " successfully added");
            orderedByField.clear();
        }
    }

    private void markAsCompletedHandler(Order t) {
        ordersInProgressList.remove(t);
        t.complete();
        ordersCompletedList.add(t);
        notificationArea1.setText("Order #" + t.getId() + " completed!");
    }
    
    private void addDriverHandler() {

        String firstName =  firstNameField.getText().toString();
        String lastName =  lastNameField.getText().toString();
        // check for errors
        if ( firstName.length()== 0 || lastName.length() == 0) {
            notificationArea2.setText("Enter both a first and last name for the driver!");
        } 
        else { // ok to add the Driver

            Deliverer t =  new Deliverer(firstName, lastName);
 
            driverList.add(t); 

            notificationArea2.setText("Driver " 	
                   +  t.getFull() +  " successfully added");
            firstNameField.clear();
            lastNameField.clear();
            buildDriverCombo();
        }
    }

    private void removeDriverHandler(Deliverer t) {
        if (driverList.size() <= 1) {
            notificationArea2.setText("Need at least one driver!");
        }
        else if (t != null) {
            driverList.remove(t); 
            notificationArea2.setText("Driver " +  t.getFull() +  " successfully removed");
        }
        buildDriverCombo();
    }

    private void addRestaurantHandler() {

        String name =  restaurantNameField.getText().toString();
        // check for errors
        if ( name.length()== 0) {
            notificationArea2.setText("Enter a name for the restaurant!");
        } 
        else { // ok to add the Restaurant

            Restaurant t =  new Restaurant(name);
 
            restaurantList.add(t); 

            notificationArea3.setText("Restaurant " 	
                   +  t.getName() +  " successfully added");
            restaurantNameField.clear();
            buildRestaurantCombo();
        }
    }

    private void removeRestaurantHandler(Restaurant t) {
        if (restaurantList.size() <= 1) {
            notificationArea3.setText("Need at least one restaurant!");
        }
        else if (t != null) {
            restaurantList.remove(t); 
            notificationArea3.setText("Restaurant " +  t.getName() +  " successfully removed");
        }
        buildRestaurantCombo();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
    
}