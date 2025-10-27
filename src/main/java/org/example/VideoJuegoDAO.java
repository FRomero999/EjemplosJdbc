package org.example;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class VideoJuegoDAO implements DAO<VideoJuego> {

    private DataSource dataSource;

    public VideoJuegoDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Optional<VideoJuego> save(VideoJuego videojuego) {
        String sql = "INSERT INTO videojuegos VALUES (0,?,?,?,?,?)";
        try(Connection conn = dataSource.getConnection();
            PreparedStatement pst = conn.prepareStatement(sql,PreparedStatement.RETURN_GENERATED_KEYS);
            ){
            pst.setString(1, videojuego.getNombre());
            pst.setString(2, videojuego.getDesarrollador());
            pst.setInt(3, videojuego.getAño());
            pst.setString(4, videojuego.getGenero());
            pst.setString(5, videojuego.getPlataforma());

            Integer res = pst.executeUpdate();
            if(res>0){
                ResultSet keys = pst.getGeneratedKeys();
                keys.next();
                videojuego.setId(keys.getInt(1));
                return Optional.of(videojuego);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<VideoJuego> update(VideoJuego videojuego) {

        String sql = "UPDATE videojuegos SET nombre=?, desarrollador=?, anio_lanzamiento=?, genero=?, plataforma=? WHERE id = ?";
        try(Connection conn = dataSource.getConnection();
            PreparedStatement pst = conn.prepareStatement(sql);
        ){
            pst.setString(1, videojuego.getNombre());
            pst.setString(2, videojuego.getDesarrollador());
            pst.setInt(3, videojuego.getAño());
            pst.setString(4, videojuego.getGenero());
            pst.setString(5, videojuego.getPlataforma());
            pst.setInt(6, videojuego.getId());

            Integer res = pst.executeUpdate();
            if(res>0){
                return Optional.of(videojuego);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<VideoJuego> delete(VideoJuego videoJuego) {

        String sql = "DELETE FROM videojuegos WHERE id = ?";
        try(Connection conn = dataSource.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ){
            stmt.setInt(1, videoJuego.getId());
            Integer resultado = stmt.executeUpdate();

            if(resultado>0){
              return Optional.of(videoJuego);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return Optional.empty();
    }

    @Override
    public List<VideoJuego> findAll() {

        ArrayList<VideoJuego> listado = new ArrayList<>();
        String sql = "SELECT * FROM videojuegos";
        try(Connection conn = dataSource.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet resultado = stmt.executeQuery(sql);
            ){
            while(resultado.next()){
                VideoJuego videojuego = new VideoJuego();
                videojuego.setId(resultado.getInt(1));
                videojuego.setNombre(resultado.getString("nombre"));
                videojuego.setDesarrollador(resultado.getString(3));
                videojuego.setPlataforma(resultado.getString("plataforma"));
                videojuego.setAño(resultado.getInt(4));
                videojuego.setGenero(resultado.getString(5));
                listado.add(videojuego);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return listado;
    }

    @Override
    public Optional<VideoJuego> findById(Integer id) {

        try(Connection conn = dataSource.getConnection()){
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM videojuegos WHERE id = ?");
            stmt.setInt(1, id);
            ResultSet resultado = stmt.executeQuery();
            if(resultado.next()){
                VideoJuego videojuego = new VideoJuego();
                videojuego.setId(resultado.getInt(1));
                videojuego.setNombre(resultado.getString("nombre"));
                videojuego.setDesarrollador(resultado.getString(3));
                videojuego.setPlataforma(resultado.getString("plataforma"));
                videojuego.setAño(resultado.getInt(4));
                videojuego.setGenero(resultado.getString(5));
                return Optional.of(videojuego);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    /* Métodos tipicos de repositorio - Capa de negocio */

    public Optional<VideoJuego> saveOrUpdate(VideoJuego videojuego){
        if(exists(videojuego)) return update(videojuego);
        else return save(videojuego);

    }

    public Boolean exists(VideoJuego videojuego){
        return (findById(videojuego.getId()).isPresent());
    }

    public Optional<VideoJuego> deleteById(Integer id){
        Optional<VideoJuego> juego = findById(id);
        /* return Optional.ofNullable( delete( juego.get() ).get() );*/
        if( juego.isPresent()) return( delete(juego.get()) );
        else return Optional.empty();
    }

    /* Métodos adicionales de lectura - Queries */

    public Optional<VideoJuego> findByNombre(String nombre){
        try(Connection conn = dataSource.getConnection()){
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM videojuegos WHERE nombre = ?");
            stmt.setString(1, nombre);
            ResultSet resultado = stmt.executeQuery();
            if(resultado.next()){
                VideoJuego videojuego = new VideoJuego();
                videojuego.setId(resultado.getInt(1));
                videojuego.setNombre(resultado.getString("nombre"));
                videojuego.setDesarrollador(resultado.getString(3));
                videojuego.setPlataforma(resultado.getString("plataforma"));
                videojuego.setAño(resultado.getInt(4));
                videojuego.setGenero(resultado.getString(5));
                return Optional.of(videojuego);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    public List<VideoJuego> findAllByPlataforma(String plataforma){

        ArrayList<VideoJuego> listado = new ArrayList<>();
        String sql = "SELECT * FROM videojuegos WHERE plataforma = ?";
        try(Connection conn = dataSource.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
        ){
            stmt.setString(1, plataforma);
            ResultSet resultado = stmt.executeQuery();
            while(resultado.next()){
                VideoJuego videojuego = new VideoJuego();
                videojuego.setId(resultado.getInt(1));
                videojuego.setNombre(resultado.getString("nombre"));
                videojuego.setDesarrollador(resultado.getString(3));
                videojuego.setPlataforma(resultado.getString("plataforma"));
                videojuego.setAño(resultado.getInt(4));
                videojuego.setGenero(resultado.getString(5));
                listado.add(videojuego);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return listado;
    }

    public List<VideoJuego> findAllByNombreContaining(String nombre){
        var salida = new ArrayList<VideoJuego>();
        try(Connection conn = dataSource.getConnection()){
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM videojuegos WHERE nombre LIKE ? ");
            stmt.setString(1, "%"+nombre+"%");
            ResultSet resultado = stmt.executeQuery();
            while(resultado.next()){
                VideoJuego videojuego = new VideoJuego();
                videojuego.setId(resultado.getInt(1));
                videojuego.setNombre(resultado.getString("nombre"));
                videojuego.setDesarrollador(resultado.getString(3));
                videojuego.setPlataforma(resultado.getString("plataforma"));
                videojuego.setAño(resultado.getInt(4));
                videojuego.setGenero(resultado.getString(5));
                salida.add(videojuego);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return salida;
    }

    public List<VideoJuego> findAllByNombre(String nombre){

        return new ArrayList<>();
    }

/*
    public List<VideoJuego> findAllGeneric(String sql, Object o) {
        ...
         Consulta sql: conexión, prepared Statetement, Resulset, while(.next), return
    }
*/
    /*
    public List<VideoJuego> findAllByYear(Integer year){

        String sql = "SELECT * FROM videojuegos WHERE year = ?";
        findAllGeneric(sql, year);
    }

     */

}
