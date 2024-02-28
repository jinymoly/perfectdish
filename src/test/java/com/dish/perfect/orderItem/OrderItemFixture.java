package com.dish.perfect.orderItem;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.dish.perfect.menu.domain.Availability;
import com.dish.perfect.menu.domain.CourseType;
import com.dish.perfect.menu.domain.Menu;
import com.dish.perfect.menuBoard.MenuBoardFixture;
import com.dish.perfect.menuBoard.service.MenuBoardService;
import com.dish.perfect.orderItem.dto.request.OrderItemRequest;
import com.dish.perfect.orderItem.domain.OrderItemStatus;

public class OrderItemFixture {

    @Autowired
    private MenuBoardService mBoardService;

    private MenuBoardFixture mBoardFixture = new MenuBoardFixture();

    public List<Menu> fixtureCommonMenus() {
        mBoardService.saveCommonMenus(mBoardFixture.requestCommonsA());
        mBoardService.saveCommonMenus(mBoardFixture.requestCommonsB());
        mBoardService.saveCommonMenus(mBoardFixture.requestCommonsC());
        mBoardService.saveCommonMenus(mBoardFixture.requestCommonsD());

        List<Menu> fixC = mBoardService.findCommonMenus();
        return fixC;
    }

    public Optional<List<Menu>> fixtureDisMenus() {
        mBoardService.saveDiscountMenus(mBoardFixture.requestDiscountsA());
        mBoardService.saveDiscountMenus(mBoardFixture.requestDiscountsB());
        mBoardService.saveDiscountMenus(mBoardFixture.requestDiscountsC());
        mBoardService.saveDiscountMenus(mBoardFixture.requestDiscountsD());

        Optional<List<Menu>> fixD = mBoardService.findDiscountMenus();
        return fixD;
    }

    public List<Menu> fixtureAllMenu() {
        fixtureCommonMenus();
        fixtureDisMenus();
        List<Menu> allMenus = mBoardService.getAllMenus();
        return allMenus;
    }

    public OrderItemRequest orderItemRequestA = OrderItemRequest.builder().tableNo(2).menu(menu1()).count(2).status(OrderItemStatus.CREATED).build();
    public OrderItemRequest orderItemRequestB = OrderItemRequest.builder().tableNo(3).menu(menu2()).count(1).status(OrderItemStatus.CREATED).build();
    public OrderItemRequest orderItemRequestC = OrderItemRequest.builder().tableNo(3).menu(menu3()).count(3).status(OrderItemStatus.CREATED).build();
    public OrderItemRequest orderItemRequestD = OrderItemRequest.builder().tableNo(3).menu(menu1()).count(5).status(OrderItemStatus.CREATED).build();
    public OrderItemRequest orderItemRequestE = OrderItemRequest.builder().tableNo(3).menu(menu2()).count(2).status(OrderItemStatus.CREATED).build();
    public OrderItemRequest orderItemRequestF = OrderItemRequest.builder().tableNo(7).menu(menu3()).count(4).status(OrderItemStatus.CREATED).build();



    public Menu menu1() {
        return Menu.builder()
        .courseType(CourseType.T_EPPETIZER)
        .menuName("메뉴A")
        .description("최고급 버터로 4시간 정성스레 카라멜라이징한 양파로 만든 수프")
        .price(13000)
        .isDiscounted(false)
        .availability(Availability.AVAILABLE)
        .build();
    }
        

    public Menu menu2() {
        return Menu.builder()
            .courseType(CourseType.T_MAIN)
            .menuName("메뉴B")
            .description("고기에 소스 부어서 구우면 됨")
            .price(30000)
            .isDiscounted(false)
            .availability(Availability.AVAILABLE)
            .build();
    }

    public Menu menu3() {
        return Menu.builder()
        .courseType(CourseType.T_DESSERT)
        .menuName("메뉴C")
        .description("달지 않아 맛있는 치즈 케이크")
        .price(15000)
        .isDiscounted(true)
        .availability(Availability.AVAILABLE)
        .build();
    }

    public Menu menu4() {
        return Menu.builder()
        .courseType(CourseType.T_EPPETIZER)
        .menuName("메뉴E")
        .description("최고급 버터로 4시간 정성스레 카라멜라이징한 양파로 만든 수프")
        .price(13000)
        .isDiscounted(true)
        .availability(Availability.AVAILABLE)
        .build();
    }
        

    public Menu menu5() {
        return Menu.builder()
            .courseType(CourseType.T_MAIN)
            .menuName("메뉴F")
            .description("고기에 소스 부어서 구우면 됨")
            .price(30000)
            .isDiscounted(true)
            .availability(Availability.AVAILABLE)
            .build();
    }

    public Menu menu6() {
        return Menu.builder()
        .courseType(CourseType.T_DESSERT)
        .menuName("메뉴G")
        .description("달지 않아 맛있는 치즈 케이크")
        .price(15000)
        .isDiscounted(true)
        .availability(Availability.AVAILABLE)
        .build();
    }
}
