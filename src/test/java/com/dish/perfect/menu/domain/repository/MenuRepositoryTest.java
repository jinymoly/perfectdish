package com.dish.perfect.menu.domain.repository;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.TestPropertySource;

import com.dish.perfect.imageManager.domain.ImageFile;
import com.dish.perfect.menu.domain.Availability;
import com.dish.perfect.menu.domain.CourseType;
import com.dish.perfect.menu.domain.Menu;
import com.dish.perfect.menu.dto.request.MenuRequest;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
@TestPropertySource(properties = {"file.dir=src/test/resources/img/"})
public class MenuRepositoryTest {
    
    @Autowired
    private InMemoryMenuRepository menuRepository;


    @AfterEach
    void clearMenuImgInTest(){
        String dirPath = "src/test/resources/img/";
        deleteFileAfterTest(dirPath);
    }

    @Test
    @DisplayName("map vs set 생성 - 어떤 컬렉션에 메뉴를 넣을까")
    void which_collection_is_better1() throws IOException {
        int virtualMenus = 1000;
        
        long mapPerformance = hashMapCreation(virtualMenus);
        long setPerformance = hashSetCreation(virtualMenus);
        
        log.info("mapCreatePerformance={}", mapPerformance+"ms");
        log.info("setCreatePerformance={}", setPerformance+"ms");
    }
    
    @Test
    @DisplayName("map vs set 조회 - 어떤 컬렉션에 메뉴를 넣을까")
    void which_collection_is_better2() throws IOException {
        int virtualMenus = 1000;
        
        long mapPerformance = hashMapLookup(virtualMenus);
        long setPerformance = hashSetLookup(virtualMenus);
    
        log.info("mapSearchPerformance={}", mapPerformance+"ms");
        log.info("setSearchPerformance={}", setPerformance+"ms");
    }

    @Test
    @DisplayName("메뉴 저장 확인")
    void savethemenu() throws IOException{
        MockMultipartFile mockMFile = new MockMultipartFile("fixtureMap2","map02Test.png", MediaType.IMAGE_PNG_VALUE, "map02Test".getBytes());
        
        MenuRequest menuDtoA = MenuRequest.builder()
        .title("아그작사과구름스테이크")
        .description("특제 소스에 4시간 졸인 후 48시간 1도에서 숙성")
        .courseType(CourseType.T_MAIN)
        .price(52000)
        .menuImgFile(mockMFile)
        .availability(Availability.AVAILABLE)
        .build();

        MenuRequest menuDtoB = MenuRequest.builder()
        .title("로맨틱 허니 갈릭 쉬림프")
        .description("신선한 새우에 특제 허니 갈릭 소스가 어우러진 로맨틱한 디너")
        .courseType(CourseType.T_MAIN)
        .price(45000)
        .menuImgFile(mockMFile)
        .availability(Availability.AVAILABLE)
        .build();
        
        Menu saveMenuA = menuRepository.save(menuDtoA);
        Menu saveMenuB = menuRepository.save(menuDtoB);
        log.info("saveMenu={}, {}", saveMenuA, saveMenuB);
        
    }
    
    @Test
    @DisplayName("코스타입별 메뉴 반환")
    void findByCourseType() throws IOException{
        MockMultipartFile mockMFile = new MockMultipartFile("Test","TestMock.jpeg", MediaType.IMAGE_JPEG_VALUE, "TestMock".getBytes());
                                    
        MenuRequest dtoA = MenuRequest.builder()
                                    .title("이것은 A메뉴다")
                                    .description("이것은 메뉴 설명")
                                    .courseType(CourseType.T_MAIN)
                                    .price(500000)
                                    .menuImgFile(mockMFile)
                                    .availability(Availability.AVAILABLE)
                                    .build();

        MenuRequest dtoB = MenuRequest.builder()
                                    .title("이것은 B메뉴다")
                                    .description("이것은 메뉴 설명")
                                    .courseType(CourseType.T_DESSERT)
                                    .price(24000)
                                    .menuImgFile(mockMFile)
                                    .availability(Availability.UNAVAILABLE)
                                    .build();
        
        MenuRequest dtoC = MenuRequest.builder()
                                    .title("이것은 C메뉴다")
                                    .description("이것은 메뉴 설명")
                                    .courseType(CourseType.T_MAIN)
                                    .price(60000)
                                    .menuImgFile(mockMFile)
                                    .availability(Availability.AVAILABLE)
                                    .build();

        menuRepository.save(dtoA);
        menuRepository.save(dtoB);
        menuRepository.save(dtoC);

        List<Menu> findByCourseType = menuRepository.findByCourseType(CourseType.T_MAIN);
        log.info("{}", findByCourseType.toString());
        assertTrue(findByCourseType.stream().allMatch(menu -> menu.getCourseType().equals(CourseType.T_MAIN)));

    }

    @Test
    @DisplayName("isAvailable? 메뉴 반환")
    void findByAvailability() throws IOException{
        MockMultipartFile mockMFile = new MockMultipartFile("Test","TestMock.jpeg", MediaType.IMAGE_JPEG_VALUE, "TestMock".getBytes());

        MenuRequest dtoA = MenuRequest.builder()
                                    .title("이것은 A메뉴다")
                                    .description("이것은 메뉴 설명")
                                    .courseType(CourseType.T_MAIN)
                                    .price(500000)
                                    .menuImgFile(mockMFile)
                                    .availability(Availability.UNAVAILABLE)
                                    .build();
                                    
        MenuRequest dtoB = MenuRequest.builder()
                                    .title("이것은 B메뉴다")
                                    .description("이것은 메뉴 설명")
                                    .courseType(CourseType.T_DESSERT)
                                    .price(24000)
                                    .menuImgFile(mockMFile)
                                    .availability(Availability.UNAVAILABLE)
                                    .build();
        
        MenuRequest dtoC = MenuRequest.builder()
                                    .title("이것은 C메뉴다")
                                    .description("이것은 메뉴 설명")
                                    .courseType(CourseType.T_MAIN)
                                    .price(60000)
                                    .menuImgFile(mockMFile)
                                    .availability(Availability.AVAILABLE)
                                    .build();

        menuRepository.save(dtoA);
        menuRepository.save(dtoB);
        menuRepository.save(dtoC);

        List<Menu> availableMenus = menuRepository.findByAvaility(Availability.AVAILABLE);
        log.info("{}", availableMenus.toString());
        assertTrue(availableMenus.stream().allMatch(menu -> menu.getAvailability().equals(Availability.AVAILABLE)));
    }

    /**
     * 테스트 종료 후 생성된 이미지 파일을 삭제
     */
    public static void deleteFileAfterTest(String dirPath){
        File dirFile = new File(dirPath);
        File[] savedTestFiles = dirFile.listFiles();
        if(savedTestFiles != null){
            for(File file : savedTestFiles){
                file.delete();
            }
        }
    }

    /**
     * hashMap 생성 성능 확인
     * @param virtualMenus
     * @return
     * @throws IOException
     */
    private long hashMapCreation(int virtualMenus) throws IOException{
        long startTime = System.currentTimeMillis();

        Map<Long, Menu> menuMap = new HashMap<>();
        for(int i = 0; i < virtualMenus; i++){
            Map<Long, Menu> fixtureMap = fixtureMap();
            long idCount = fixtureMap.keySet().iterator().next();
            menuMap.put(idCount, fixtureMap.get(idCount));
        }
        long endTime = System.currentTimeMillis();
        return endTime - startTime;

    }

    /**
     * hashMap 조회 성능 확인
     * @param virtualMenus
     * @return
     * @throws IOException
     */
    private long hashMapLookup(int virtualMenus) throws IOException{
        long startTime = System.currentTimeMillis();

        Map<Long, Menu> menuMap = new HashMap<>();
        for(int i = 0; i< virtualMenus; i++){
            Map<Long, Menu> fixtureMenu = fixtureMap();
            long idCount = fixtureMenu.keySet().iterator().next();
            menuMap.put(idCount, fixtureMenu.get(idCount));
        }
        for(Map.Entry<Long, Menu> entry : menuMap.entrySet()){
            if(entry.getValue().getMenuName().equals("도미 카르파치오")){
                Menu value = entry.getValue();
                log.info("current menu searched by hashMap={}", value.toString());
            };
        }
        long endTime = System.currentTimeMillis();

        return endTime - startTime;
    }

    /**
     * hashSet 생성 성능 확인
     * @param virtualMenus
     * @return
     * @throws IOException
     */
    private long hashSetCreation(int virtualMenus) throws IOException{
        long startTime = System.currentTimeMillis();

        Set<Menu> menuSet = new HashSet<>();
        for(int i = 0; i < virtualMenus; i++){
            menuSet.add(fixtureSet());
        }
        long endTime = System.currentTimeMillis();
        return endTime - startTime;
    }

    /**
     * hashSet 조회 성능 확인
     * @param virtualMenus
     * @return
     * @throws IOException
     */
    private long hashSetLookup(int virtualMenus) throws IOException{
        long startTime = System.currentTimeMillis();

        Set<Menu> menuSet = new HashSet<>();
        for(int i = 0; i < virtualMenus; i++){
            menuSet.add(fixtureSet());
        }
        for(Menu menu : menuSet){
            if(menu.getMenuName().equals("도미 카르파치오")){
                log.info("current menu searched by hashSet={}", menu.toString());
            }
        }
        long endTime = System.currentTimeMillis();
        return endTime - startTime;
    }

    private final Menu fixtureSet() throws IOException {
        MockMultipartFile mockMFile = new MockMultipartFile("menuImg","testImg.png", MediaType.IMAGE_PNG_VALUE, "testImg".getBytes());

        MenuRequest menuDto = MenuRequest.builder()
                                        .title("도미 카르파치오")
                                        .description("오늘의 소스와 레몬주스가 들어간 애피타이저")
                                        .courseType(CourseType.T_EPPETIZER)
                                        .price(23000)
                                        .menuImgFile(mockMFile)
                                        .availability(Availability.AVAILABLE)
                                        .build();
            Menu saveMenu = menuRepository.save(menuDto);
            log.info("savedMenuSet={}", saveMenu.toString());
            return saveMenu;
                                        
    }

    /**
     * key가 Long(id)인 HashMap
     * @return
     * @throws IOException
     */
    private final Map<Long, Menu> fixtureMap() throws IOException {
        Map<Long, Menu> menuMap = new HashMap<>();
        AtomicLong idCount = new AtomicLong(0);
        long incrementAndGet = idCount.incrementAndGet();

        MockMultipartFile mockMFile = new MockMultipartFile("menuImg","testImg.png", MediaType.IMAGE_PNG_VALUE, "testImg".getBytes());

        MenuRequest menuDto = MenuRequest.builder()
                                        .title("도미 카르파치오")
                                        .description("오늘의 소스와 레몬주스가 들어간 애피타이저")
                                        .courseType(CourseType.T_EPPETIZER)
                                        .price(23000)
                                        .menuImgFile(mockMFile)
                                        .availability(Availability.AVAILABLE)
                                        .build();
        Menu menu = Menu.builder()
                        .courseType(menuDto.getCourseType())
                        .menuName(menuDto.getTitle())
                        .description(menuDto.getDescription())
                        .price(menuDto.getPrice())
                        .isDiscounted(false)
                        .menuImg(new ImageFile(menuDto.getMenuImgUrl(), "서버 저장 이미지"))
                        .availability(menuDto.getAvailability())
                        .build();
        menuMap.put(incrementAndGet, menu);
        log.info("savedMenuMap={}", menuMap.toString());
        return menuMap;
    }
}
