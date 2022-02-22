package view.output;

import domain.lotto.*;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class OutputView {
    public static void printLottoTickets(LottoTickets lottoTickets) {
        List<LottoTicket> tickets = lottoTickets.getLottoTickets();
        System.out.printf("%d개를 구매했습니다.\n", tickets.size());
        System.out.println(makeLottoTicketsString(tickets));
        System.out.println();
    }

    private static String makeLottoTicketsString(List<LottoTicket> tickets) {
        return tickets.stream()
                .map(OutputView::makeLottoTicketString)
                .collect(Collectors.joining("\n"));
    }

    private static String makeLottoTicketString(LottoTicket lottoTicket) {
        return lottoTicket.getLottoNumbers().stream()
                .mapToInt(LottoNumber::getNumber)
                .mapToObj(String::valueOf)
                .collect(Collectors.joining(", ", "[", "]"));
    }

    public static void printLottoGameResults(LottoGameResults lottoGameResults) {
        System.out.println("당첨 통계");
        System.out.println("---------");
        System.out.println(makeRewardsString(lottoGameResults));
        System.out.printf("총 수익률은 %.2f %% 입니다", lottoGameResults.getReturnToInvestment());
    }

    private static String makeRewardsString(LottoGameResults lottoGameResults) {
        return Arrays.stream(Rank.values())
                    .filter(rank -> rank != Rank.FAILED)
                    .sorted(Comparator.comparing(Rank::getCountOfMatch))
                    .map(rank -> String.format("%d개 일치 (%d원) - %d개", rank.getCountOfMatch(), rank.getReward(), lottoGameResults.getRankCountOf(rank)))
                    .collect(Collectors.joining("\n"));
    }
}