package br.com.eacf.app.endpoint;

import br.com.eacf.app.entity.ProducerInterval;
import br.com.eacf.app.service.ProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("producer")
public class ProducerController {

    private ProducerService service;

    @Autowired
    public ProducerController(ProducerService service){
        this.service = service;
    }

    @GetMapping("/")
    public ResponseEntity<ProducerInterval> findAll(){
        return ResponseEntity.ok(service.getProducerIntervals());
    }

}
