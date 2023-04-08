package com.example.Controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import service.MathService;

@RestController
public class MathController {

    private final MathService service = new MathService();

    @RequestMapping(value = "/sum/{numberOne}/{numberTwo}", method = RequestMethod.GET)
    public Double sum(
            @PathVariable(value = "numberOne") String numberOne,
            @PathVariable(value = "numberTwo") String numberTwo) {
        return service.calcular("sum", numberOne, numberTwo);
    }

    @RequestMapping(value = "/sub/{numberOne}/{numberTwo}")
    public Double subtracao(@PathVariable(value = "numberOne") String numberOne,
                      @PathVariable(value = "numberTwo") String numberTwo) {
        return service.calcular("sub", numberOne, numberTwo);
    }

    @RequestMapping(value = "/multi/{numberOne}/{numberTwo}")
    public Double multiplicacao(@PathVariable(value = "numberOne") String numberOne,
                      @PathVariable(value = "numberTwo") String numberTwo) {
        return service.calcular("multi", numberOne, numberTwo);
    }

    @RequestMapping(value = "/div/{numberOne}/{numberTwo}")
    public Double divisao(@PathVariable(value = "numberOne") String numberOne,
                                @PathVariable(value = "numberTwo") String numberTwo) {
        return service.calcular("div", numberOne, numberTwo);
    }

    @RequestMapping(value = "/media/{numberOne}/{numberTwo}")
    public Double media(@PathVariable(value = "numberOne") String numberOne,
                          @PathVariable(value = "numberTwo") String numberTwo) {
        return service.calcular("media", numberOne, numberTwo);
    }

    @RequestMapping(value = "/raizQuadrada/{numberOne}")
    public Double raizQuadrada(@PathVariable(value = "numberOne") String numberOne) {
        return service.calcular("raizQuadrada", numberOne, "");
    }
}
