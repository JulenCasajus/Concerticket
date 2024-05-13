module eus.ehu.sharetrip {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires org.hibernate.orm.core;
    requires org.apache.logging.log4j;
    requires jakarta.persistence;
    requires java.desktop;

    opens eus.ehu.concerticket.domain to javafx.base, org.hibernate.orm.core;
    opens eus.ehu.concerticket.ui to javafx.fxml;
    opens eus.ehu.concerticket.uicontrollers to javafx.fxml;
    exports eus.ehu.concerticket.ui;
}
