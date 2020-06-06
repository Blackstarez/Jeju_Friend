module jeju_friend {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;

    opens jeju_friend.application to javafx.fxml;
    exports jeju_friend.application;
    exports jeju_friend.Elements;
    opens jeju_friend.controller to javafx.fxml;
}