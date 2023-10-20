package web.webTest.filaA;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import web.webTest.TestBaseTodoLy;

public class Ejercicio3 extends TestBaseTodoLy {
    String nameProject = "NuevoProyecto_Acker";
    String email = "pabloParcial2@pablo.com";
    String psw = "pablo123";

    @Test
    public void verifyCreateUserAndProjectTest() {
        mainPage.signUpButton.click();
        signUpPage.fullNameTextbox.setText("PabloA");
        signUpPage.emailTextbox.setText(email);
        signUpPage.passwordTextbox.setText(psw);
        signUpPage.acceptTermsButton.click();
        signUpPage.signUpButton.click();
        Assertions.assertTrue(centralSection.openSettingsButton.isControlDisplayed(), "ERROR! No se pudo crear el usuario");
        projectSection.addProjectButton.click();
        projectSection.projectNameTextBox.setText(nameProject);
        projectSection.projectNameButton.click();
        Assertions.assertTrue(projectSection.addNameToProjectLabel(nameProject).isControlDisplayed(), "ERROR! No se pudo crear el proyecto");
    }
}
