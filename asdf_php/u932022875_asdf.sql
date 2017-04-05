
-- phpMyAdmin SQL Dump
-- version 3.5.2.2
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Apr 04, 2017 at 07:12 AM
-- Server version: 10.1.21-MariaDB
-- PHP Version: 5.2.17

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `u932022875_asdf`
--

-- --------------------------------------------------------

--
-- Table structure for table `allotment`
--

CREATE TABLE IF NOT EXISTS `allotment` (
  `prim` mediumint(9) NOT NULL AUTO_INCREMENT,
  `exam_id` mediumint(9) NOT NULL,
  `prof_id` mediumint(9) NOT NULL,
  `position` varchar(10) NOT NULL,
  `room` varchar(10) NOT NULL,
  PRIMARY KEY (`prim`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=10 ;

--
-- Dumping data for table `allotment`
--

INSERT INTO `allotment` (`prim`, `exam_id`, `prof_id`, `position`, `room`) VALUES
(1, 1, 3, 'ss', '404B'),
(2, 1, 5, 'js', '404B'),
(3, 2, 6, 'ss', '404B'),
(4, 2, 7, 'js', '404B'),
(5, 3, 8, 'ss', '405'),
(6, 3, 9, 'js', '405'),
(7, 4, 10, 'ss', '409'),
(8, 5, 11, 'ss', '303'),
(9, 6, 12, 'ss', '302');

-- --------------------------------------------------------

--
-- Table structure for table `exam`
--

CREATE TABLE IF NOT EXISTS `exam` (
  `prim` mediumint(9) NOT NULL AUTO_INCREMENT,
  `scode` varchar(10) NOT NULL,
  `edate` date NOT NULL,
  `etime` time NOT NULL,
  `eslot` varchar(5) NOT NULL DEFAULT 'mor',
  `room` varchar(6) NOT NULL,
  `etimediff` smallint(5) NOT NULL DEFAULT '0',
  PRIMARY KEY (`prim`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=7 ;

--
-- Dumping data for table `exam`
--

INSERT INTO `exam` (`prim`, `scode`, `edate`, `etime`, `eslot`, `room`, `etimediff`) VALUES
(1, 'FEMECH', '2017-04-04', '09:30:00', 'mor', '404B', 0),
(2, 'SECG', '2017-04-04', '15:00:00', 'eve', '404B', 0),
(3, 'TEOS', '2017-04-05', '09:30:00', 'mor', '405', 0),
(4, 'TEMCC', '2017-04-05', '15:00:00', 'eve', '409', 0),
(5, 'TESE', '2017-04-03', '15:00:00', 'eve', '303', 0),
(6, 'BEML', '2017-04-03', '09:00:00', 'mor', '302', 0);

-- --------------------------------------------------------

--
-- Table structure for table `notifications`
--

CREATE TABLE IF NOT EXISTS `notifications` (
  `prim` int(11) NOT NULL AUTO_INCREMENT,
  `date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `message` varchar(250) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`prim`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=11 ;

--
-- Dumping data for table `notifications`
--

INSERT INTO `notifications` (`prim`, `date`, `message`) VALUES
(1, '2017-03-24 20:58:18', 'this is first test message'),
(2, '2017-03-24 20:58:18', 'this is second test message'),
(3, '2017-03-24 20:59:22', 'this is third test message'),
(4, '2017-03-25 05:25:16', 'hey there everyone'),
(5, '2017-03-25 14:59:46', 'test notification'),
(6, '2017-03-26 09:44:52', 'test '),
(7, '2017-03-26 13:39:21', 'hello'),
(8, '2017-03-26 18:18:01', ''),
(9, '2017-03-26 19:01:21', 'testing '),
(10, '2017-03-27 05:24:12', 'Kathi');

-- --------------------------------------------------------

--
-- Table structure for table `prof_preferences`
--

CREATE TABLE IF NOT EXISTS `prof_preferences` (
  `prim` mediumint(9) NOT NULL AUTO_INCREMENT,
  `prof_id` mediumint(9) NOT NULL,
  `ptime` time NOT NULL,
  `pdate` date NOT NULL,
  `ptype` tinyint(4) NOT NULL DEFAULT '1',
  `pslot` int(10) NOT NULL,
  PRIMARY KEY (`prim`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `prof_profile`
--

CREATE TABLE IF NOT EXISTS `prof_profile` (
  `prim` mediumint(9) NOT NULL AUTO_INCREMENT,
  `name` varchar(200) NOT NULL,
  `rank` varchar(10) DEFAULT NULL,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `duty_amount` smallint(6) NOT NULL,
  `type` varchar(10) NOT NULL DEFAULT 'user',
  `dept` varchar(10) NOT NULL DEFAULT 'none',
  PRIMARY KEY (`prim`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=14 ;

--
-- Dumping data for table `prof_profile`
--

INSERT INTO `prof_profile` (`prim`, `name`, `rank`, `username`, `password`, `duty_amount`, `type`, `dept`) VALUES
(1, 'prof1', NULL, 'prof1', 'profone', 0, 'user', 'comps'),
(2, 'a', 'ss', 'aa', 'a1', 3, 'user', 'it'),
(3, 'comps1', 'ss', 'comps1', 'comps1', 3, 'user', 'comps'),
(5, 'comps2', 'js', 'comps2', 'comps2', 6, 'user', 'comps'),
(6, 'it1', 'ss', 'it1', 'it1', 3, 'user', 'it'),
(7, 'it2', 'js', 'it2', 'it2', 6, 'user', 'it'),
(8, 'extc1', 'ss', 'extc1', 'extc1', 3, 'user', 'extc'),
(9, 'extc2', 'js', 'extc2', 'extc2', 6, 'user', 'extc'),
(10, 'comps', 'ss', 'comps', 'comps', 3, 'comps', 'comps'),
(11, 'it', 'ss', 'it', 'it', 3, 'it', 'it'),
(12, 'extc', 'ss', 'extc', 'extc', 3, 'extc', 'extc'),
(13, 'admin', 'ss', 'admin', 'admin', 0, 'admin', 'admin');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
