package br.com.gerenciamento.controller;

import br.com.gerenciamento.model.Usuario;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UsuarioControllerTest {

    @Autowired
    UsuarioController usuarioController;

    @Test
    public void cadastroTest() {

        ModelAndView mv = usuarioController.cadastrar();

        Assert.assertEquals("login/cadastro", mv.getViewName());
        Assert.assertNotNull(mv.getModel().get("usuario"));

    }


    @Test
    public void cadastrarUsuarioTest() throws Exception {

        Usuario usuario = new Usuario();
        usuario.setEmail("test@email.com");
        usuario.setUser("Joao");
        usuario.setSenha("100006");

        ModelAndView mv = usuarioController.cadastrar(usuario);

        Assert.assertEquals("redirect:/", mv.getViewName());
    }


    @Test
    public void indexTest() {

        ModelAndView mv = usuarioController.index();

        Assert.assertEquals("home/index", mv.getViewName());
        Assert.assertNotNull(mv.getModel().get("aluno"));
    }

    @Test
    public void loginTest() {

        ModelAndView mv= usuarioController.login();

        Assert.assertEquals("login/login", mv.getViewName());
        Assert.assertNotNull(mv.getModel().get("usuario"));

    }


}
