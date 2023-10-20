package web.webTest.filaB;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import web.webTest.TestBaseTodoLy;

import java.util.Random;

public class Ejercicio3 extends TestBaseTodoLy {
    private Random rand = new Random();
    String email = "pablo"+rand.nextInt(10000)+"@pablo.com";
    String psw = "P4bl012345";
    @Test
    public void verifyCreateUserAndProject() throws InterruptedException {
        mainTodoistPage.loginButton.click();
        mainTodoistPage.registerButton.click();
        loginPage.emailTextBox.setText(email);
        loginPage.passwordTextBox.setText(psw);
        loginPage.loginButton.click();
        loginPage.nameTxtBox.setText("PabloAcker1");
        loginPage.continueButton.click();
        Thread.sleep(2000);
        loginPage.personalButton.click();
        Thread.sleep(5000);
        loginPage.openTodoIstButton.click();
        Thread.sleep(2000);
        //verificar que se creo el usuario deslogeando y volviendo a logear
        navBarSection.openInfoButton.click();
        navBarSection.logoutButton.click();
        mainTodoistPage.loginButton.click();
        loginPage.emailTextBox.setText(email);
        loginPage.passwordTextBox.setText(psw);
        loginPage.loginButton.click();
        Thread.sleep(3000);
        Assertions.assertTrue(navBarSection.openInfoButton.isControlDisplayed(), "ERROR!! No se pudo iniciar sesion");
        //Crear project:
        String nameProject = "Project_Acker";
        lateralProjectSection.projectSectionLabel.click();
        lateralProjectSection.addProjectButton.click();
        Thread.sleep(2000);
        addProjectPopUp.projectNameTextbox.setText(nameProject);
        Thread.sleep(2000);
        addProjectPopUp.changeColorButton.click();
        addProjectPopUp.selectBlueColor.click();
        Thread.sleep(2000);
        addProjectPopUp.submitNameButton.click();
        // verificar si el proyecto se creo
        Assertions.assertEquals(lateralProjectSection.checkNewProject(nameProject).getTextValue(), nameProject, "ERROR! No se pudo crear el proyecto");
    }
}
