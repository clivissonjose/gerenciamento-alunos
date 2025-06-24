package br.com.gerenciamento.service;

import br.com.gerenciamento.exception.EmailExistsException;
import br.com.gerenciamento.model.Usuario;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UsuarioServiceTest {

    @Autowired
    private ServiceUsuario serviceUsuario;


    // Teste 1: usuário com email existente
    @Test
    public void salvarUsuarioEmailExistenteTest() throws Exception {

        Usuario usuario = new Usuario();

        usuario.setEmail("email@test.com");
        usuario.setSenha("83628783");
        usuario.setUser("Cesar de Barbosa");

        serviceUsuario.salvarUsuario(usuario);

        Usuario usuario2 = new Usuario();

        usuario2.setEmail("email@test.com");
        usuario2.setSenha("6374843");
        usuario2.setUser("Camila Santos");

        // Tenta salvar novo usuário com email já existente
        Assert.assertThrows(EmailExistsException.class, () -> {
            this.serviceUsuario.salvarUsuario(usuario2);});

    }

    // teste 2: logar usuário corretamente
    @Test
    public void loginUserTest() throws Exception {
        Usuario usuario = new Usuario();

        usuario.setEmail("email11@test.com");
        usuario.setSenha("836ppp83");
        usuario.setUser("Cesar de Santos");

        serviceUsuario.salvarUsuario(usuario);

        Usuario usuarioRetornado = serviceUsuario.loginUser(usuario.getUser(), usuario.getSenha());

        assertEquals("Cesar de Santos", usuarioRetornado.getUser());
        assertEquals("email11@test.com", usuarioRetornado.getEmail());

    }

    // Teste 3: logar usuário inexistente
    @Test
    public void logarUsuarioInexistente(){

        Usuario usuarioLogado = serviceUsuario.loginUser("Usuario teste", "76957123");
        Assert.assertNull("Usuário deve ser nulo pois não existe no sistema", usuarioLogado);

    }

    // Teste 4: Logar usuário com senha incorreta

    @Test
    public void logarUsuarioSenhaIncorreta() throws Exception {

        Usuario usuario = new Usuario();

        usuario.setEmail("emailNovo@test.com");
        usuario.setSenha("836ppp83");
        usuario.setUser("Cesar de Santos");

        this.serviceUsuario.salvarUsuario(usuario);

        Usuario usuarioRetornado = serviceUsuario.loginUser(usuario.getUser(), "wrongPassword");
        Assert.assertNull("Usuário deve ser nulo pois a senha está incorreta",usuarioRetornado);

    }

}
