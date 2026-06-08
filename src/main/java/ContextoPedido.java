public class ContextoPedido {
    private EstadoPedido estado;
    public ContextoPedido(){
        this.estado = new EstadoDePedidoBorrador(this);
    }
}
