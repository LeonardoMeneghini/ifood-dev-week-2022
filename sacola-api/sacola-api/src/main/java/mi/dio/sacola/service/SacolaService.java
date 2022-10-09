package mi.dio.sacola.service;

import mi.dio.sacola.model.Item;
import mi.dio.sacola.model.Sacola;
import mi.dio.sacola.resource.dto.ItemDto;

public interface SacolaService {
    Item incluirItemNaSacola(ItemDto itemDto);
    Sacola verSacola(Long id);
    Sacola fecharSacola(Long id, int formaPagamento);
}
