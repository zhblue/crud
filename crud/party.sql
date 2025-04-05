-- 党支部表
CREATE TABLE party_branch (
    id INT PRIMARY KEY AUTO_INCREMENT,
    branch_name VARCHAR(50) NOT NULL COMMENT '支部名称',
    secretary VARCHAR(20) COMMENT '支部书记',
    established_date DATE NOT NULL COMMENT '成立日期',
    INDEX idx_branch (branch_name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 党员表
CREATE TABLE party_member (
    id INT PRIMARY KEY AUTO_INCREMENT,
    member_name VARCHAR(20) NOT NULL COMMENT '姓名',
    party_branch_id INT NOT NULL COMMENT '所属支部ID',
    student_id VARCHAR(15) NOT NULL COMMENT '学号',
    join_date DATE NOT NULL COMMENT '入党日期',
    position VARCHAR(20) COMMENT '党内职务',
    INDEX idx_member (member_name),
    UNIQUE KEY uk_student (student_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 组织活动表
CREATE TABLE party_activity (
    id INT PRIMARY KEY AUTO_INCREMENT,
    activity_name VARCHAR(50) NOT NULL COMMENT '活动名称',
    party_branch_id INT NOT NULL COMMENT '主办支部ID',
    activity_type VARCHAR(20) NOT NULL COMMENT '活动类型',
    start_time DATETIME NOT NULL COMMENT '开始时间',
    end_time DATETIME NOT NULL COMMENT '结束时间',
    INDEX idx_activity (activity_name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 材料类型表
CREATE TABLE material_type (
    id INT PRIMARY KEY AUTO_INCREMENT,
    type_name VARCHAR(30) NOT NULL COMMENT '类型名称',
    description VARCHAR(200) COMMENT '类型说明',
    INDEX idx_material_type (type_name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 党建材料表
CREATE TABLE party_material (
    id INT PRIMARY KEY AUTO_INCREMENT,
    material_name VARCHAR(100) NOT NULL COMMENT '材料名称',
    party_member_id INT NOT NULL COMMENT '提交人ID',
    party_activity_id INT COMMENT '关联活动ID',
    material_type_id INT NOT NULL COMMENT '材料类型ID',
    submit_time DATETIME NOT NULL COMMENT '提交时间',
    file_path VARCHAR(200) NOT NULL COMMENT '文件路径',
    INDEX idx_material (material_name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 会议记录表
CREATE TABLE meeting_record (
    id INT PRIMARY KEY AUTO_INCREMENT,
    meeting_title VARCHAR(100) NOT NULL COMMENT '会议标题',
    party_branch_id INT NOT NULL COMMENT '所属支部ID',
    meeting_time DATETIME NOT NULL COMMENT '会议时间',
    presenter VARCHAR(20) NOT NULL COMMENT '主讲人',
    content TEXT NOT NULL COMMENT '会议内容',
    INDEX idx_meeting (meeting_title)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
