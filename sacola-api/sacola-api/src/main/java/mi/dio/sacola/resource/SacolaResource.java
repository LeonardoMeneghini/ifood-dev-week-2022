package mi.dio.sacola.resource;

import lombok.RequiredArgsConstructor;
import mi.dio.sacola.model.Item;
import mi.dio.sacola.model.Sacola;
import mi.dio.sacola.resource.dto.ItemDto;
import mi.dio.sacola.service.SacolaService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ifood-devweek/sacolas")
@RequiredArgsConstructor
public class SacolaResource {
    private final SacolaService sacolaService;

    @PostMapping
    public Item incluirItemNaSacola(ItemDto itemDto){
        return sacolaService.incluirItemNaSacola(itemDto);
    }
    @GetMapping("/{id}")
    public Sacola verSacola(@PathVariable("id") Long id){
        return sacolaService.verSacola (id);

    }
    @PatchMapping("/fecharSAcola/{sacolaId}")
    public Sacola fecharSacola(@PathVariable("sacolaId") Long sacolaId, @RequestParam("formaPagamento") int formaPagamento){
        return sacolaService.fecharSacola(sacolaId, formaPagamento);

    }

}
