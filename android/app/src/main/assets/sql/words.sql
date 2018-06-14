-- phpMyAdmin SQL Dump
-- version 4.0.10.19
-- https://www.phpmyadmin.net
--
-- 主机: localhost
-- 生成日期: 2018-06-13 14:38:54
-- 服务器版本: 5.5.54-log
-- PHP 版本: 5.4.45

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- 数据库: `words`
--

-- --------------------------------------------------------

--
-- 表的结构 `grammar`
--

CREATE TABLE IF NOT EXISTS `grammar` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `question` varchar(255) NOT NULL,
  `options` varchar(255) NOT NULL,
  `answer` varchar(255) NOT NULL,
  `score` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 COMMENT='语法题库' AUTO_INCREMENT=6 ;

--
-- 转存表中的数据 `grammar`
--

INSERT INTO `grammar` (`id`, `question`, `options`, `answer`, `score`) VALUES
(1, 'There _____ an English film at the cinema now.', 'will have;is going to have;is going to be;is', 'is', 20),
(2, 'The picture _______ nice.', 'looks;is looked;look;is looking', 'looks', 20),
(3, 'She ______ down and soon falls asleep.', 'live;lain;laid;sits', 'sits', 20),
(4, 'They _____ the office in time very morning.', 'reach to;arrived; went;get to', 'arrived', 20),
(5, 'We shall go to Shanghai on business before you _____ back next week.', 'will come;came;would come;come', 'will come', 20);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
