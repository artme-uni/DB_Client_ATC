package model.support;

public class SupportElementsCTN {
    private final SupportElementsModel elementsModel = new SupportElementsModel();

    public SupportElementsCTN() {
        elementsModel.addSupportElement("get_age");
        elementsModel.addSupportElement("clients_ages");
        elementsModel.addSupportElement("caller_addresses");
        elementsModel.addSupportElement("get_long_distance_debt_age");
        elementsModel.addSupportElement("get_subscription_debt_age");
        elementsModel.addSupportElement("days_between");
        elementsModel.addSupportElement("is_debtor");
        elementsModel.addSupportElement("get_debtor_count");
        elementsModel.addSupportElement("exchange_type");
        elementsModel.addSupportElement("get_max_debt");
        elementsModel.addSupportElement("free_phones_possibilities");
        elementsModel.addSupportElement("debtors_info");

//        elementsModel.addSupportElement("City_exchanges_id_tr");
//        elementsModel.addSupportElement("Institutional_exchanges_id_tr");
//        elementsModel.addSupportElement("Departmental_exchanges_id_tr");


        elementsModel.addSupportElement("Telephone_exchange_id_tr");
        elementsModel.addSupportElement("Connection_prices_id_tr");
        elementsModel.addSupportElement("LD_call_prices_id_tr");
        elementsModel.addSupportElement("Phone_types_id_tr");
        elementsModel.addSupportElement("Subscription_fees_id_tr");
        elementsModel.addSupportElement("Phone_numbers_id_tr");
        elementsModel.addSupportElement("Clients_id_tr");
        elementsModel.addSupportElement("Callers_id_tr");
        elementsModel.addSupportElement("Address_id_tr");
        elementsModel.addSupportElement("Phones_id_tr");
        elementsModel.addSupportElement("Public_phones_id_tr");
        elementsModel.addSupportElement("Long_distance_calls_id_tr");
        elementsModel.addSupportElement("Connection_requests_id_tr");
        elementsModel.addSupportElement("Installing_possibilities_id_tr");

        elementsModel.addSupportElement("Telephone_exchanges_seq");
        elementsModel.addSupportElement("Connection_prices_seq");
        elementsModel.addSupportElement("LD_call_prices_seq");
        elementsModel.addSupportElement("Phone_types_seq");
        elementsModel.addSupportElement("Subscription_fees_seq");
        elementsModel.addSupportElement("Phone_numbers_seq");
        elementsModel.addSupportElement("Clients_seq");
        elementsModel.addSupportElement("Callers_seq");
        elementsModel.addSupportElement("Address_seq");
        elementsModel.addSupportElement("Phones_seq");
        elementsModel.addSupportElement("Public_phones_seq");
        elementsModel.addSupportElement("Long_distance_calls_seq");
        elementsModel.addSupportElement("Connection_requests_seq");
        elementsModel.addSupportElement("Installing_possibilities_seq");

        elementsModel.addSupportElement("Balances_debt_date_tr");
        elementsModel.addSupportElement("Balances_debt_date_update_tr");
        elementsModel.addSupportElement("calculate_call_cost");
        elementsModel.addSupportElement("changeCallerDebt");
        elementsModel.addSupportElement("Calls_price_calculating_tr");
        elementsModel.addSupportElement("Connection_requests_date_tr");

    }

    public SupportElementsModel getModel(){
        return elementsModel;
    }
}
