module test {
	requires javafx.controls;
	requires javafx.graphics;
	requires javafx.fxml;
	requires javafx.base;
	requires javafx.media;
	requires bluecove;
	requires java.logging;
	requires java.sql;
	requires jfoenix;
	requires javax.persistence;
	requires jdk.jdi;
	requires java.desktop;
	opens application to javafx.fxml;
	exports application;
}
