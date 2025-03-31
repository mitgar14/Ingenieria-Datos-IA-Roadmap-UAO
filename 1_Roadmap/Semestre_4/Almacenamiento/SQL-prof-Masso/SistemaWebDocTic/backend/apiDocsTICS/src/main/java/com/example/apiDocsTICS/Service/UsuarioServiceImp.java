package com.example.apiDocsTICS.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.apiDocsTICS.Model.UsuarioModel;
import com.example.apiDocsTICS.Repository.IUsuarioRepository;
import com.example.apiDocsTICS.Exception.RecursoNoEncontradoException;

@Service
public class UsuarioServiceImp implements IUsuarioService {
    @Autowired IUsuarioRepository usuarioRepository;
    @Override
    public String crearUsuario(UsuarioModel usuario){
        usuarioRepository.save(usuario);
        return "El usuario "+ usuario.getNombreUsuario() + " Con correo "+ usuario.getCorreoUsuario()+" Fue Creado con exito";
    }

    @Override
    public UsuarioModel buscarUsuarioPorId(int idUsuario) {
        Optional<UsuarioModel> usuariorecuperado = usuarioRepository.findById(idUsuario);
        return usuariorecuperado.orElseThrow(()-> new RecursoNoEncontradoException("El usuario con el id " + idUsuario + " no existe"));
    }

    @Override
    public List<UsuarioModel> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    @Override
    public String eliminarUsuario(int idUsuario){
        if(usuarioRepository.existsById(idUsuario)) {
            usuarioRepository.deleteById(idUsuario);
            return "El usuario " + idUsuario + " eliminado con exito";
        } else {
            throw new RecursoNoEncontradoException("El usuario con id "+idUsuario+" no existe");
        }
    }


}
