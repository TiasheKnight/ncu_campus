-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Dec 28, 2023 at 06:20 PM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `campus`
--

-- --------------------------------------------------------

--
-- Table structure for table `message`
--

CREATE TABLE `message` (
  `id` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `friend_user_id` int(11) DEFAULT NULL,
  `message_content` text DEFAULT NULL,
  `message_time_stamp` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `message`
--

INSERT INTO `message` (`id`, `user_id`, `friend_user_id`, `message_content`, `message_time_stamp`) VALUES
(1, 1, 7, '你好', '12/10/23 22:57\r'),
(2, 1, 5, '早安', '11/30/23 16:39\r'),
(3, 3, 6, '安安', '11/29/23 19:09\r'),
(4, 3, 5, '嗨唷', '12/1/23 8:20\r'),
(5, 4, 6, '台北雙魚28y', '12/18/23 3:25\r'),
(6, 5, 3, '走啊你不敢啦', '12/15/23 3:33\r'),
(7, 5, 1, '夜光閃亮亮復仇鬼', '12/17/23 21:29\r'),
(8, 6, 3, '好餓喔吃啥', '2023/12/13\r'),
(9, 6, 4, '太誇張了吧', '12/12/23 12:12\r'),
(10, 7, 1, '要去傳情嗎', '12/27/23 18:30');
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
