import { NavigationItem } from "src/@vex/interfaces/navigation-item.interface";

export const NAVIGATION: NavigationItem[] = [
    {
        type: 'link',
        label: 'Danh mục',
        icon: 'mat:trending_up',
        route: '/admin/category',
        routerLinkActiveOptions: { exact: true },
    },
    {
        type: 'link',
        label: 'Bài viết',
        icon: 'mat:trending_up',
        route: '/admin/article',
        routerLinkActiveOptions: { exact: true },
    },
    {
        type: 'link',
        label: 'Bài viết Slider',
        icon: 'mat:trending_up',
        route: '/admin/article-slider',
        routerLinkActiveOptions: { exact: true },
    },
    {
        type: 'link',
        label: 'Bài viết trang chủ',
        icon: 'mat:trending_up',
        route: '/admin/article-home',
        routerLinkActiveOptions: { exact: true },
    },
    {
        type: 'link',
        label: 'Menu',
        icon: 'mat:trending_up',
        route: '/admin/page-menu',
        routerLinkActiveOptions: { exact: true },
    },
//     {
//       type: 'link',
//       label: 'Website',
//       icon: 'mat:trending_up',
//       route: '/admin/website',
//       routerLinkActiveOptions: { exact: true },
//   },
];
