package com.aydsii.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.aydsii.demo.exception.ClienteNoEncontradoException;
import com.aydsii.demo.exception.ProductoNoEncontradoException;
import com.aydsii.demo.exception.ProductoSinStockException;
import com.aydsii.demo.exception.StockInsuficienteException;
import com.aydsii.demo.model.dto.AgregarCarritoRequestDTO;
import com.aydsii.demo.model.dto.CarritoRespuestaDTO;
import com.aydsii.demo.model.dto.ItemCarritoDTO;
import com.aydsii.demo.model.tablas.Cliente;
import com.aydsii.demo.model.tablas.ItemCarrito;
import com.aydsii.demo.model.tablas.Producto;
import com.aydsii.demo.repository.ClienteRepository;
import com.aydsii.demo.repository.ItemCarritoRepository;
import com.aydsii.demo.repository.ProductoRepository;

@Service
public class CarritoService {

    private final ItemCarritoRepository carritoRepository;
    private final ClienteRepository clienteRepository;
    private final ProductoRepository productoRepository;

    public CarritoService(ItemCarritoRepository carritoRepository, ClienteRepository clienteRepository,
            ProductoRepository productoRepository) {
        this.carritoRepository = carritoRepository;
        this.clienteRepository = clienteRepository;
        this.productoRepository = productoRepository;
    }

    public CarritoRespuestaDTO agregarProducto(AgregarCarritoRequestDTO producto) {

        Cliente cliente = clienteRepository.findById(producto.clienteDni())
                .orElseThrow(() -> new ClienteNoEncontradoException(producto.clienteDni()));

        Producto productoCatalogo = productoRepository.findById(producto.productoCodigo())
                .orElseThrow(() -> new ProductoNoEncontradoException(
                        "No se encontró el producto con código " + producto.productoCodigo() + "."));

        int disponible = productoCatalogo.getStockDisponible();

        if (disponible <= 0) {
            throw new ProductoSinStockException(productoCatalogo.getNombre());
        }

        // si el producto ya está en el carrito, se suma a lo que había
        ItemCarrito item = carritoRepository
                .findByClienteDniAndProductoCodigo(producto.clienteDni(), producto.productoCodigo())
                .orElse(null);
        int enCarrito = 0;
        if (item != null) {
            enCarrito = item.getCantidad();
        }

        if (enCarrito + producto.cantidad() > disponible) {
            throw new StockInsuficienteException(productoCatalogo.getNombre(), disponible, enCarrito,
                    producto.cantidad());
        }

        if (item == null) {
            item = new ItemCarrito();
            item.setCliente(cliente);
            item.setProducto(productoCatalogo);
        }

        item.setCantidad(enCarrito + producto.cantidad());
        carritoRepository.save(item);

        return armarCarrito(producto.clienteDni());
    }

    // arma la respuesta con todas las líneas del carrito y el total.
    private CarritoRespuestaDTO armarCarrito(String dni) {
        List<ItemCarritoDTO> items = new ArrayList<>();
        double total = 0;

        for (ItemCarrito i : carritoRepository.findByClienteDni(dni)) {
            Producto p = i.getProducto();
            double subtotal = p.getPrecioPack() * i.getCantidad();
            total += subtotal;
            items.add(new ItemCarritoDTO(p.getCodigo(), p.getNombre(), p.getDescripcion(),
                    i.getCantidad(), p.getPrecioPack(), subtotal));
        }

        return new CarritoRespuestaDTO(dni, items, total);
    }
}
