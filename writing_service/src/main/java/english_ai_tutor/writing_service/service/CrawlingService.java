package english_ai_tutor.writing_service.service;

import english_ai_tutor.writing_service.domain.News;
import english_ai_tutor.writing_service.domain.Sentence;
import english_ai_tutor.writing_service.dto.NewsDto;
import english_ai_tutor.writing_service.dto.SentenceDto;
import english_ai_tutor.writing_service.repository.NewsRepository;
import english_ai_tutor.writing_service.repository.SentenceRepository;
import lombok.RequiredArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CrawlingService {
    private final TranslationService translationService;
    private final NewsRepository newsRepository;
    private final SentenceRepository sentenceRepository;

    public void crawling(){
        WebDriver driver = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));

        driver.get("https://www.newsinlevels.com");
        // 광고 제거
        WebElement adCloseButton = driver.findElement(By.className("pum-close"));

        wait.until(ExpectedConditions.elementToBeClickable(adCloseButton));

        adCloseButton.click();



        for (int i = 3; i < 5; i++) {
            driver.manage().timeouts().implicitlyWait(Duration.ofMillis(1000));

            if(i == 4) continue;

            NewsDto news = null;
            List<SentenceDto> sentenceList = new ArrayList<>();

            for (int level = 1; level <= 3; level++) {
                WebElement title = driver.findElement(By.xpath(String.format("/html/body/main/div/div/div/div[1]/div/div[1]/div[%d]/div[2]/div[1]/h3/a", i)));

                String newsTitle = title.getText();

                WebElement news_click = driver.findElement(By.xpath(String.format("/html/body/main/div/div/div/div[1]/div/div[1]/div[%d]/div[2]/div[3]/ul/li[%d]/a", i, level)));
                Integer newsLevel = news_click.getText().charAt(6) - '0';

                news_click.click();

                news = NewsDto.builder()
                        .title(newsTitle)
                        .build();

                List<WebElement> pTags = driver.findElement(By.id("nContent")).findElements(By.tagName("p"));

                driver.manage().timeouts().implicitlyWait(Duration.ofMillis(1000));

                List<String> sentences = new ArrayList<>();

                for (int k = 1; k < pTags.size() - 2; k++){
                    sentences.addAll(Arrays.stream(pTags.get(k).getText().split("\\.\\s")).toList());
                }

                sentenceList.addAll(translationService.translate(sentences, level));

                driver.navigate().back();
            }
            save(news, sentenceList);
        }
        driver.close();
    }

    private void save(NewsDto newsDto, List<SentenceDto> sentenceList) {
        News news = News.builder()
                .title(newsDto.getTitle())
                .build();
        newsRepository.save(news);

        sentenceList.stream().forEach(sentenceDto -> {
            sentenceRepository.save(Sentence.builder()
                    .english(sentenceDto.getEnglish())
                    .korean(sentenceDto.getKorean())
                    .level(sentenceDto.getLevel())
                    .news(news)
                    .build()
            );
        });
    }
}
