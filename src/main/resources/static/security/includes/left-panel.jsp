<%@ page import="ge.imperio.utils.MGLUserUtils" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<div class="login-info">
    <span>
        <a href="javascript:void(0);" id="show-shortcut" data-action="toggleShortcut">
            <img src="<%=MGLUserUtils.getLogoPath()%>" alt="me" class="online"/>
            <span data-localize="">
                 <security:authorize access="isAuthenticated()">
                     <table>
                         <tr>
                             <td>
                                 <%=MGLUserUtils.getCurrentUser().getUserName()%>
                             </td>
                         </tr>
                     </table>
                 </security:authorize>
            </span>
            <i class="fa fa-angle-down"></i>
        </a>
    </span>
</div>

<navigation>
    <nav:group data-icon="fa fa-lg fa-fw fa-legal" title="მენეჯმენტი">
        <% if (MGLUserUtils.hasAuthority("ORGANISATIONS_LIST")) { %>
        <nav:item data-view="/organisation" data-icon="fa fa-lg fa-fw fa-building" title="ორგანიზაციები"/>
        <nav:item data-view="/banner" data-icon="fa fa-lg fa-fw fa-building" title="ბანერები"/>
        <% } %>
        <% if (MGLUserUtils.hasAuthority("SHOW_PERMISSION")) { %>
        <nav:item data-view="/permission" data-icon="fa fa-lg fa-fw fa-th-list" title="უფლებები"/>
        <% } %>
        <% if (MGLUserUtils.hasAuthority("SHOW_ROLE")) { %>
        <nav:item data-view="/role" data-icon="fa fa-lg fa-fw fa-group" title="უფლებათა ჯგუფი"/>
        <% } %>
        <% if (MGLUserUtils.hasAuthority("SHOW_ROLE_PERM")) { %>
        <nav:item data-view="/rolepermission" data-icon="fa fa-lg fa-fw fa-warning" title="მომხ. როლები"/>
        <% } %>
        <% if (MGLUserUtils.hasAuthority("SHOW_USER")) { %>
        <nav:item data-view="/user" data-icon="fa fa-lg fa-fw fa-user" title="მომხმარებლები"/>
        <% } %>
        <nav:item data-view="/salary" data-icon="fa fa-lg fa-fw fa-dollar" title="სალარო"/>
        <nav:item data-view="/invoices" data-icon="fa fa-lg fa-fw fa-envelope" title="ინვოისები"/>
        <nav:item data-view="/organisationEmailReceivers" data-icon="fa fa-lg fa-fw fa-envelope" title="მეილები"/>
    </nav:group>
    <nav:group data-icon="fa fa-lg fa-fw fa-bookmark-o" title="რეპორტინგი">
        <nav:item data-view="/reporting" data-icon="fa fa-lg fa-fw fa-bar-chart" title="რეპორტის გენერაცია"/>
        <nav:item data-view="/graphs" data-icon="fa fa-lg fa-fw fa-bar-chart" title="გრაფიკული რეპორტები"/>
        <nav:item data-view="/transactions" data-icon="fa fa-lg fa-fw fa-table" title="ტრანზაქციები"/>
    </nav:group>
    <nav:group data-icon="fa fa-lg fa-fw fa-list-ul" title="სერვისები">
        <nav:item data-view="/serviceCategory" data-icon="fa fa-lg fa-fw fa-angle-double-right" title="კატეგორიები"/>
        <nav:item data-view="/services" data-icon="fa fa-lg fa-fw fa-angle-double-right" title="სერვისები"/>
    </nav:group>
    <nav:group data-icon="fa fa-lg fa-fw fa-product-hunt" title="პროდუქტები">
        <nav:item data-view="/productLog" data-icon="fa fa-lg fa-fw fa-database" title="ლოგი"/>
        <nav:item data-view="/products" data-icon="fa fa-lg fa-fw fa-product-hunt" title="სია"/>
        <nav:item data-view="/organisationPayments" data-icon="fa fa-lg fa-fw fa-money" title="გადახდები"/>
    </nav:group>
    <nav:group data-icon="fa fa-lg fa-fw fa-clock-o" title="კალენდარი">
        <nav:item data-view="/events" data-icon="fa fa-lg fa-fw fa-clock-o" title="კალენდარი"/>
        <nav:item data-view="/position" data-icon="fa fa-lg fa-fw fa-sitemap" title="პოზიციები"/>
        <nav:item data-view="/member" data-icon="fa fa-lg fa-fw fa-user" title="თანაშმრომლები"/>
        <nav:item data-view="/job" data-icon="fa fa-lg fa-fw fa-bullseye" title="დავალებები"/>
    </nav:group>
    <nav:group data-icon="fa fa-lg fa-fw fa-child" title="კლიენტები">
        <nav:item data-view="/person" data-icon="fa fa-lg fa-fw fa-table" title="სია"/>
        <nav:item data-view="/personBalance" data-icon="fa fa-lg fa-fw fa-users" title="კლიენტის ბალანსი"/>
    </nav:group>
    <nav:group data-icon="fa fa-lg fa-fw fa-building-o" title="(R) ორგანიზაციები">
        <nav:item data-view="/remoteOrganisation" data-icon="fa fa-lg fa-fw fa-list-alt" title="სია"/>
        <nav:item data-view="/remoteOrganisationCodes" data-icon="fa fa-lg fa-fw fa-barcode" title="კოდები"/>
    </nav:group>
    <nav:group data-icon="fa fa-lg fa-fw fa-bell" title="შეტყობინებები">
        <nav:item data-view="/notification" data-icon="fa fa-lg fa-fw fa-exclamation" title="შეტყობინებები"/>
        <nav:item data-view="/userOrderRequest" data-icon="fa fa-lg fa-fw fa-bell" title="ჯავშნები"/>
    </nav:group>
    <nav:group data-icon="fa fa-lg fa-fw fa-wrench" title="Other">
        <nav:item data-view="/email" data-icon="fa fa-lg fa-fw fa-envelope" title="მეილის კონფიგურაცია"/>
        <nav:item data-view="/supplier" data-icon="fa fa-lg fa-fw fa-shopping-basket" title="მომწოდებლები"/>
        <nav:item data-view="/facebook" data-icon="fa fa-lg fa-fw fa-facebook" title="Facebook"/>
        <nav:group data-icon="fa fa-lg fa-fw fa-user" title="ნოტიფიკაციები">
            <nav:item data-view="/notificationsettings" data-icon="fa fa-lg fa-fw fa-android" title="Android"/>
            <nav:item data-view="/sendpushes" data-icon="fa fa-lg fa-fw fa-apple" title="iOS"/>
        </nav:group>
        <nav:group data-icon="fa fa-lg fa-fw fa fa-credit-card" title="გადახდები">
            <nav:item data-view="/tbcconfig" data-icon="fa fa-lg fa-fw fa-credit-card" title="TBC"/>
            <nav:item data-view="/tbcpayconfig" data-icon="fa fa-lg fa-fw fa-credit-card" title="TBC Pay"/>
            <nav:item data-view="/libertyconfig" data-icon="fa fa-lg fa-fw fa-credit-card" title="Liberty"/>
        </nav:group>
    </nav:group>
</navigation>
<span class="minifyme" data-action="minifyMenu">
    <i class="fa fa-arrow-circle-left hit"></i>
</span>