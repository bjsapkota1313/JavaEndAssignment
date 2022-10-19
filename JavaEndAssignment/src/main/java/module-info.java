    module com.exam.javaendassignment {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.exam.javaendassignment to javafx.fxml;
    exports com.exam.javaendassignment;
    exports Controllers;
    exports Model;
    opens Controllers to javafx.fxml;

}