
import java.sql.PreparedStatement;
import java.sql.Connection;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProdutosDAO {

    private conectaDAO conexao;
    private Connection conn;
    private PreparedStatement stmt;
    private ResultSet rs;
    ArrayList<ProdutosDTO> listagem = new ArrayList<>();

    public ProdutosDAO() {
        this.conexao = new conectaDAO();
        this.conn = this.conexao.connectDB();
    }

    public void cadastrarProduto(ProdutosDTO produto) {

        String sql = "INSERT INTO produtos(nome, valor, status) VALUES (?, ?, ?)";
        stmt = null;

        try {
            stmt = this.conn.prepareStatement(sql);
            stmt.setString(1, produto.getNome());
            stmt.setInt(2, produto.getValor());
            stmt.setString(3, produto.getStatus());

            stmt.executeUpdate();

        } catch (SQLException ex) {
            System.out.println("Erro ao conectar: " + ex.getMessage());
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                } else if (this.conn != null) {
                    this.conn.close();
                }
            } catch (SQLException ex) {
                System.out.println("Erro ao fechar recursos: " + ex.getMessage());
            }
        }

    }

    public ArrayList<ProdutosDTO> listarProdutos() {
        String sql = "SELECT * FROM produtos";
        
        try{
            stmt = this.conn.prepareStatement(sql);
            rs = stmt.executeQuery();
            
            while(rs.next()){
                ProdutosDTO produtos = new ProdutosDTO();
                
                produtos.setId(rs.getInt("id"));
                produtos.setNome(rs.getString("nome"));
                produtos.setValor(rs.getInt("valor"));
                produtos.setStatus(rs.getString("status"));
                
                listagem.add(produtos);
            }
        } catch (SQLException ex) {
            System.out.println("Não foi possível exibir a listagem: " + ex.getMessage());
        }
        
        
        
        return listagem;
    }

}
