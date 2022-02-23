package domain.lotto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LottoGameResults {

    private final Money money;
    private final LottoTickets lottoTickets;
    private final WinningTicket winningTicket;
    private final Map<Rank, Integer> rankCounts;

    public LottoGameResults(Money money, LottoTickets lottoTickets, WinningTicket winningTicket) {
        this.money = money;
        this.lottoTickets = lottoTickets;
        this.winningTicket = winningTicket;
        this.rankCounts = setupRankCounts();
    }

    private Map<Rank, Integer> setupRankCounts() {
        Map<Rank, Integer> rankCounts = initRankCounts();
        calculateRankCounts(rankCounts);
        return rankCounts;
    }

    private Map<Rank, Integer> initRankCounts() {
        Map<Rank, Integer> rankCounts = new HashMap<>();
        for (Rank rank : Rank.values()) {
            rankCounts.put(rank, 0);
        }
        return rankCounts;
    }

    private void calculateRankCounts(Map<Rank, Integer> rankCounts) {
        List<LottoTicket> tickets = lottoTickets.getLottoTickets();
        for (LottoTicket ticket : tickets) {
            Rank resultRank = winningTicket.match(ticket);
            rankCounts.put(resultRank, rankCounts.get(resultRank) + 1);
        }
    }

    public double getReturnToInvestment() {
        long totalRewards = getTotalRewards();
        double profit = totalRewards - money.getPurchaseAmount();
        double returnOfInvestment = (profit) / money.getPurchaseAmount() * 100;
        return returnOfInvestment;
    }

    private long getTotalRewards() {
        long totalRewards = 0;
        for (Map.Entry<Rank, Integer> entry : rankCounts.entrySet()) {
            long reward = entry.getKey().getReward();
            int count = entry.getValue();
            long totalReward = reward * count;
            totalRewards += totalReward;
        }
        return totalRewards;
    }

    public int getRankCountOf(Rank rank) {
        return rankCounts.get(rank);
    }
}
