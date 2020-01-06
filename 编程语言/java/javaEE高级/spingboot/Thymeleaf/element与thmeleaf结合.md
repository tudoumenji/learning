```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8"/>
    <link rel="stylesheet" href="https://unpkg.com/element-ui/lib/theme-chalk/index.css"/>
</head>
<body>
<div class="panel-title">Operation Log</div>

<div id="app1">
    <template>
        <div class="column df">
            <div class="ml15">
                <span>Field Name:</span>
                <el-select v-model="operateActionSelected" placeholder="Select">
                    <el-option
                            v-for="operateActionItem in operateActionList"
                            :key="operateActionItem"
                            :label="operateActionItem"
                            :value="operateActionItem">
                    </el-option>
                </el-select>
            </div>
            <div class="ml15 mt1">
                <span>user Name:</span>
                <el-input v-model="userName" placeholder="请输入内容" id="app2" prefix-icon="el-icon-search"
                          style="width:210px"></el-input>
            </div>
        </div>
        <div class="column mt1">
            <span class="ml15">TimeRange:</span>
            <el-date-picker id="app3"
                            v-model="startDate"
                            type="date"
                            placeholder="选择日期">
            </el-date-picker>
            <span class="line">—</span>
            <el-date-picker id="app4"
                            v-model="endDate"
                            type="date"
                            placeholder="选择日期">
            </el-date-picker>

            <el-button id="app5" type="primary" v-on:click="queryOperationLog">Search</el-button>
        </div>
        <div class="column mt1">
            <span class="ml15">Operation Log List</span>
        </div>

        <el-table
                :data="tableData"
                height="600"
                border
                style="width: 100%" id="app6">
            <el-table-column
                    prop="moduleName"
                    label="Modules">
            </el-table-column>
            <el-table-column
                    prop="userName"
                    label="User Name">
            </el-table-column>
            <el-table-column
                    prop="role"
                    label="Role">
            </el-table-column>
            <el-table-column
                    prop="operateBeginTime"
                    label="Operation Time">
            </el-table-column>
            <el-table-column
                    prop="operateAction"
                    label="Field Name">
            </el-table-column>
            <el-table-column
                    prop="requirementNum"
                    label="Requirement No.">
            </el-table-column>
            <el-table-column
                    prop="details"
                    label="details">
            </el-table-column>
            <el-table-column
                    prop="result"
                    label="result">
            </el-table-column>
        </el-table>
    </template>
    <div class="block">
        <el-pagination
                @size-change="handleSizeChange"
                @current-change="handleCurrentChange"
                :current-page.sync="currentPage1"
                :page-size="pageSize"
                layout="total, prev, pager, next"
                :total="total">
        </el-pagination>
    </div>
</div>


</body>
<script src="https://unpkg.com/vue/dist/vue.js"></script>
<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
<script src="https://unpkg.com/element-ui/lib/index.js"></script>
<script src="https://unpkg.com/axios/dist/axios.min.js"></script>
<script th:inline="javascript">

    let OperateActionList = [[${OperateActionList}]];
    let module = [[${module}]];
    let project = [[${project}]];
    let initialEndDate = new Date();
    let initialBeginDate = new Date(initialEndDate);
    initialBeginDate.setDate(initialEndDate.getDate() - 30);

    new Vue({
        el: '#app1',
        data: {
            module: module,
            project: project,
            userName: '',
            startDate: initialBeginDate,
            endDate: initialEndDate,
            tableData: [],
            operateActionList: JSON.parse(OperateActionList),
            operateActionSelected: '',
            total: 0,
            pageSize: 8,
        },
        methods: {
            queryOperationLog() {
                let currentPage = 1;
                let _this = this;
                let pageSize = this.pageSize;
                let startLine = (currentPage - 1) * pageSize + 1;
                let endLine = currentPage * pageSize;
                /* let startLine = 0;
                 let endLine = 10000;*/
                $.ajax({
                    url: "/ConfigSvr/operationLog",
                    header: {
                        'cloudAuthRequest': "cloudAuthResponse"
                    },
                    data: {
                        module: _this.module,
                        project: _this.project,
                        userName: _this.userName,
                        startDate: _this.startDate,
                        endDate: _this.endDate,
                        operateAction: _this.operateActionSelected,
                        startLine: startLine,
                        endLine: endLine
                    },
                    success: function (result) {
                        _this.tableData = result;
                        _this.total = result.length;
                    }
                });
                $.ajax({
                    url: "/ConfigSvr/operationLog",
                    header: {
                        'cloudAuthRequest': "cloudAuthResponse"
                    },
                    data: {
                        module: _this.module,
                        project: _this.project,
                        userName: _this.userName,
                        startDate: _this.startDate,
                        endDate: _this.endDate,
                        operateAction: _this.operateActionSelected,
                        startLine: 0,
                        endLine: 100000
                    },
                    success: function (result) {
                        _this.total = result.length;
                    }
                });
            },
            handleSizeChange(val) {
                console.log(`每页 ${val} 条`);
            },
            handleCurrentChange(currentPage) {
                let _this = this;
                let pageSize = this.pageSize;
                let startLine = (currentPage - 1) * pageSize + 1;
                let endLine = currentPage * pageSize;
                /* let startLine = 0;
                 let endLine = 10000;*/
                $.ajax({
                    url: "/ConfigSvr/operationLog",
                    header: {
                        'cloudAuthRequest': "cloudAuthResponse"
                    },
                    data: {
                        module: _this.module,
                        project: _this.project,
                        userName: _this.userName,
                        startDate: _this.startDate,
                        endDate: _this.endDate,
                        operateAction: _this.operateActionSelected,
                        startLine: startLine,
                        endLine: endLine
                    },
                    success: function (result) {
                        _this.tableData = result;
                    }
                });

            }
        }

    });
</script>
<style>
    .df {
        display: flex;
    }

    .ml15 {
        margin-left: 15px;
    }

    .mt10 {
        margin-top: 10px;
    }

    .column {
        border: 1px solid #EBEEF5;
        padding: 10px 0;
    }

    .mt1 {
        margin-top: -1px;
    }

    .panel-title {
        height: 55px;
        line-height: 40px;
        margin-left: 5px !important;
        font-size: 20px !important;
        font-style: italic;
        color: #F56C6C !important;
        font-weight: 400 !important;
    }

    .line {
        font-weight: lighter;
        color: lightgrey;
    }
</style>
</html>
```

11
