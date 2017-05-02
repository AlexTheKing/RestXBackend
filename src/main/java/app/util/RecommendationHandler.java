package app.util;

import app.database.validator.DishValidator;
import app.model.dish.Dish;
import app.model.dish.dao.DishDAO;
import app.model.rate.Rate;
import app.model.rate.dao.RateDAO;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RecommendationHandler {

    private final RateDAO mRateDAO;
    private final DishDAO mDishDAO;

    public RecommendationHandler(DishDAO dishDAO, RateDAO rateDAO){
        mDishDAO = dishDAO;
        mRateDAO = rateDAO;
    }

    public List<Dish> getRecommendations(Session session, String appInstanceId){
        return getRecommendations(session, appInstanceId, Constants.RECOMMENDATIONS.DEFAULT_COUNT);
    }

    public List<Dish> getRecommendations(Session session, String appInstanceId, int count){
        HashMap<String, List<Rate>> ratesPerUser = getRatesPerUser(session);
        List<Rate> searchedUserRates = ratesPerUser.get(appInstanceId);
        ratesPerUser.remove(appInstanceId);
        HashMap<String, List<DishCorrelation>> correlatedRatesPerUser = new HashMap<>();
        HashMap<String, Double> coefficientPerUser = new HashMap<>();

        for (String user : ratesPerUser.keySet()) {
            List<DishCorrelation> correlations = new ArrayList<>();
            correlatedRatesPerUser.put(user, correlations);
            List<Rate> userRates = ratesPerUser.get(user);
            double pearsonCorrelation = calculatePearsonCorrelation(searchedUserRates, userRates);
            coefficientPerUser.put(user, pearsonCorrelation);

            for (Rate rate : userRates) {
                double correlatedRate = pearsonCorrelation * rate.getRate();
                correlations.add(new DishCorrelation(rate.getDish(), correlatedRate));
            }
        }

        HashMap<Dish, List<CorrelatedRate>> correlatedRatesPerDish = transformIntoDishMap(session, correlatedRatesPerUser);
        HashMap<Dish, Double> userRatePerDish = calculateUserRatePerDish(correlatedRatesPerDish, coefficientPerUser);

        return sortAndReturnCount(userRatePerDish, count);
    }

    private HashMap<Dish, Double> calculateUserRatePerDish(HashMap<Dish, List<CorrelatedRate>> correlatedRatesPerDish, HashMap<String, Double> coefficientPerUser){
        HashMap<Dish, Double> userRatePerDish = new HashMap<>();

        for (Dish dish : correlatedRatesPerDish.keySet()) {
            double rateSum = 0;
            double correlationSum = 0;

            for (CorrelatedRate correlatedRate : correlatedRatesPerDish.get(dish)) {
                rateSum += correlatedRate.getCorrelation();
                correlationSum += coefficientPerUser.get(correlatedRate.getUser());
            }

            double resultRate = 0;
            if(correlationSum != 0){
                resultRate = rateSum / correlationSum;
            }

            userRatePerDish.put(dish, resultRate);
        }

        return userRatePerDish;
    }

    private List<Dish> sortAndReturnCount(HashMap<Dish, Double> userRatePerDish, int count){
        int size = userRatePerDish.keySet().size();
        Dish[] sortedDishes = new Dish[size];
        double[] sortedRates = new double[size];
        List<Dish> resultList = new ArrayList<>();
        int index = 0;

        for (Dish dish : userRatePerDish.keySet()) {
            sortedDishes[index] = dish;
            sortedRates[index] = userRatePerDish.get(dish);
            index++;
        }

        bubbleSort(sortedDishes, sortedRates);

        for(int i = 0; i < count && i < sortedDishes.length; i++){
            resultList.add(sortedDishes[i]);
        }

        return resultList;
    }

    private void bubbleSort(Dish[] dishes, double[] rates){
        boolean isSorted = false;

        while(!isSorted) {
            isSorted = true;
            for (int i = 0; i < rates.length - 1; i++) {
                if(rates[i] < rates[i + 1]){
                    double tempRate = rates[i];
                    Dish tempDish = dishes[i];
                    rates[i] = rates[i + 1];
                    rates[i + 1] = tempRate;
                    dishes[i] = dishes[i + 1];
                    dishes[i + 1] = tempDish;
                    isSorted = false;
                }
            }
        }
    }

    private HashMap<Dish, List<CorrelatedRate>> transformIntoDishMap(Session session, HashMap<String, List<DishCorrelation>> correlatedRatePerUser){
        final HashMap<Dish, List<CorrelatedRate>> correlatedRatePerDish = new HashMap<>();
        final List<Dish> dishes = mDishDAO.getAll(session);

        for (Dish dish : dishes) {
            correlatedRatePerDish.put(dish, new ArrayList<>());
        }

        for (String user : correlatedRatePerUser.keySet()) {
            for (DishCorrelation dishCorrelation : correlatedRatePerUser.get(user)) {
                correlatedRatePerDish.get(dishCorrelation.getDish()).add(new CorrelatedRate(user, dishCorrelation.getCorrelatedRate()));
            }
        }

        return correlatedRatePerDish;
    }

    private HashMap<String, List<Rate>> getRatesPerUser(Session session){
        final HashMap<String, List<Rate>> ratesPerUser = new HashMap<>();
        final List<Rate> allRates = mRateDAO.getAll(session);

        for (Rate rate : allRates) {
            String id = rate.getAppInstanceId();

            if(!ratesPerUser.containsKey(id)) {
                ratesPerUser.put(id, new ArrayList<>());
            }

            ratesPerUser.get(id).add(rate);
        }

        return ratesPerUser;
    }

    private double calculatePearsonCorrelation(List<Rate> userRates, List<Rate> otherUserRates){
        int userSum = 0;
        int otherSum = 0;
        int userSumSquare = 0;
        int otherSumSquare = 0;
        int sumPowered = 0;
        int countCommonElements = 0;

        for (Rate userRate : userRates) {
            for (Rate otherRate : otherUserRates) {
                if(userRate.getDish().getName().equals(otherRate.getDish().getName())){
                    countCommonElements++;
                    int user = userRate.getRate();
                    int other = otherRate.getRate();
                    userSum += user;
                    otherSum += other;
                    userSumSquare += Math.pow(user, 2);
                    otherSumSquare += Math.pow(other, 2);
                    sumPowered += user * other;
                }
            }
        }

        if(countCommonElements == 0){
            return 0;
        }

        double enumerator = sumPowered - (userSum + otherSum) / countCommonElements;
        double denumerator = Math.sqrt((userSumSquare - Math.pow(userSum, 2) / countCommonElements) * (otherSumSquare - Math.pow(otherSum, 2) / countCommonElements));

        if(denumerator == 0){
            return 0;
        } else {
            return enumerator / denumerator;
        }
    }

    private class CorrelatedRate {

        private String mUser;
        private double mCorrelation;

        public CorrelatedRate(String user, double correlation) {
            mUser = user;
            mCorrelation = correlation;
        }

        public String getUser() {
            return mUser;
        }

        public void setUser(String user) {
            mUser = user;
        }

        public double getCorrelation() {
            return mCorrelation;
        }

        public void setCorrelation(double correlation) {
            mCorrelation = correlation;
        }
    }

    private class DishCorrelation {

        private Dish mDish;
        private double mCorrelatedRate;

        public DishCorrelation(Dish dish, double correlatedRate) {
            mDish = dish;
            mCorrelatedRate = correlatedRate;
        }

        public Dish getDish() {
            return mDish;
        }

        public void setDish(Dish dish) {
            mDish = dish;
        }

        public double getCorrelatedRate() {
            return mCorrelatedRate;
        }

        public void setCorrelatedRate(double correlatedRate) {
            mCorrelatedRate = correlatedRate;
        }
    }

}
