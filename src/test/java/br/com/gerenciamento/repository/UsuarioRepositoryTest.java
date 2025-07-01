package br.com.gerenciamento.repository;

import br.com.gerenciamento.model.Usuario;
import jakarta.validation.ConstraintViolationException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertTrue;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.test.annotation.Rollback;

@Transactional
@Rollback
@RunWith(SpringRunner.class)
@SpringBootTest
public class UsuarioRepositoryTest {


    @Autowired
    UsuarioRepository usuarioRepository;

    @Test
    public void findByEmailTest(){

        Usuario usuario = new Usuario();
        usuario.setEmail("emailtest@gmial.com");
        usuario.setSenha("r637362");
        usuario.setUser("Aline Gomes");
        usuario.setId(1L);

        this.usuarioRepository.save(usuario);

        Usuario usuarioRetornado = this.usuarioRepository.findByEmail("emailtest@gmial.com");
        assertTrue(usuario.getEmail().equals(usuarioRetornado.getEmail()));
        assertTrue(usuarioRetornado.getId().equals(1L));
    }

    @Test
    public void buscarLoginTest(){

        Usuario usuario = new Usuario();
        usuario.setEmail("emailtest@gmial.com");
        usuario.setSenha("r637362");
        usuario.setUser("Aline Gomes");
        usuario.setId(1L);

        this.usuarioRepository.save(usuario);

        Usuario usuarioRetornado = this.usuarioRepository.buscarLogin("Aline Gomes", "r637362");
        assertTrue(usuarioRetornado.getUser().equals("Aline Gomes"));
        assertTrue(usuarioRetornado.getSenha().equals("r637362"));

    }


    @Test
    public void usuarioComNomeCurto(){

        Usuario usuario = new Usuario();

        usuario.setUser("ab");
        usuario.setSenha("Neyeyeyey");
        usuario.setEmail("email@gmail.com");


        Assert.assertThrows(ConstraintViolationException.class, () -> {
            this.usuarioRepository.save(usuario);});

    }


    @Test
    public void usuarioComNomeMuitoLongo(){

        Usuario usuario = new Usuario();

        usuario.setUser("Nome com mais de 20 caracteres ");
        usuario.setSenha("Neyeyeyey");
        usuario.setEmail("email@.com");

        Assert.assertThrows(ConstraintViolationException.class, () -> {
            this.usuarioRepository.save(usuario);});

    }

}
