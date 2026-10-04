package mobileoperator;

import mobileoperator.data.JsonDataLoader;
import mobileoperator.model.MobileOperator;
import mobileoperator.service.TariffService;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public class ApplicationData {

    private final List<MobileOperator> operators;
    private final TariffService tariffService;

    public ApplicationData(Path dataDirectory) throws IOException {
        JsonDataLoader loader = new JsonDataLoader();

        this.operators = List.of(
                loader.loadOperator(1, "Kyivstar", dataDirectory.resolve("kyivstar")),
                loader.loadOperator(2, "Vodafone", dataDirectory.resolve("vodafone")),
                loader.loadOperator(3, "lifecell", dataDirectory.resolve("lifecell"))
        );

        this.tariffService = new TariffService();
    }

    public List<MobileOperator> getOperators() {
        return operators;
    }

    public TariffService getTariffService() {
        return tariffService;
    }

    public int getTotalClientCount() {
        return tariffService.countAllClients(operators);
    }
}