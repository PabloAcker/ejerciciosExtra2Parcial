package web.webTest.filaA;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import web.webTest.TestBaseTodoist;

public class Ejercicio4 extends TestBaseTodoist {

    @Test
    public void verifyCreateTask() throws InterruptedException {
        mainTodoistPage.loginButton.click();
        loginPage.emailTextBox.setText("pablo@pablo.com");
        loginPage.passwordTextBox.setText("P4bl0123");
        loginPage.loginButton.click();
        navBarSection.addTask.click();
        Thread.sleep(2000);
        navBarSection.taskName.setText("Task_Acker3");
        Thread.sleep(2000);
        navBarSection.buttonOk.click();
        Thread.sleep(1000);
        navBarSection.buttonCancel.click();

        Assertions.assertTrue(navBarSection.taskExists("Task_Acker3"), "ERROR! No se pudo crear la task");
    }
}
