package eus.ehu.concerticket.uicontrollers;

import eus.ehu.concerticket.ui.MainGUI;

public interface Controller {
    void setMainApp(MainGUI mainGUI);

    void setNull();

    void bookVisible(boolean b);
}