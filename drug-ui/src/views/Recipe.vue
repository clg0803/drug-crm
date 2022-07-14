<template>
  <div id="app">
    <el-container style="height: 700px; border: 1px solid #eee">

      <el-container>
        <el-header style="text-align: center; font-size: 30px">
          <span> 查看处方界面 </span>
        </el-header>

        <el-header style="text-align: right; font-size: 12px">
          <el-input
              v-model="searchValue" size="mini" clearable
              placeholder="请输入处方的医生姓名查询" style="width:200px"></el-input>
          <el-button type="primary" size="mini" @click="doFilter">查询</el-button>
          <span> {{ docType }} </span>
        </el-header>

        <el-main>
          <el-table :data="tableData.slice((currentPage-1)*pageSize, currentPage*pageSize)">

            <el-table-column prop="recipeID" label="处方流水号" width="200" align="center">
            </el-table-column>
            <el-table-column prop="docName" label="处方创建者" width="200" align="center">
            </el-table-column>
            <el-table-column prop="createTime" label="处方创建时间" width="300" style="text-align: center" align="center">
            </el-table-column>
            <el-table-column prop="detail" label="处方详情" width="300" align="center">
              <template slot-scope="scope">
                <el-button @click="open(scope.row)" class="el-icon-chat-dot-round"> 点击查看</el-button>
              </template>
            </el-table-column>

          </el-table>

          <el-pagination
              background
              @size-change="handleSizeChange"
              @current-change="handleCurrentChange"
              :current-page="currentPage"
              :page-sizes="[5, 10, 20, 40]"
              :page-size="pageSize"
              layout="total, sizes, prev, pager, next, jumper"
              :total="tableData.length"
          >
          </el-pagination>

        </el-main>


      </el-container>


    </el-container>

    <el-dialog :title="dialogTitle" width="50%" :visible.sync="iconFormVisible">
      <div>
        <table style="margin: auto;">
          <tr>
            <td>药品名称</td>
            <td> 用量</td>
          </tr>
          <tr v-for="(k, v) in this.selectedRow.detail">
            <td>{{ v }}</td>
            <td>{{ k }}</td>
          </tr>
        </table>
      </div>

      <div slot="footer" class="dialog-footer">
        <el-button @click="iconFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="iconFormVisible = false">确 定</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<style>
  table {
    border-collapse: collapse;
    margin: 0 auto;
    text-align: center;
  }

  table td, table th {
    border: 1px solid #cad9ea;
    color: #666;
    height: 30px;
  }

  table thead th {
    background-color: #CCE8EB;
    width: 100px;
  }

  table tr:nth-child(odd) {
    background: #fff;
  }

  table tr:nth-child(even) {
    background: #F5FAFA;
  }
</style>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      currentPage: 1,
      pageSize: 5,
      tableData: [],
      searchValue: "",
      docType: "",
      iconFormVisible: false,
      dialogTitle: '处方详情',
      selectedRow: ''
    }
  },
  methods: {
    doFilter() {
      console.log("into doFilter() method ... ")
      console.log(this.searchValue)
      // TODO
      let searchData = [];
      this.tableData.filter((item) => {
        if (item.docName.search(this.searchValue) !== -1) {
          searchData.push(item)
        }
      })
      console.log(searchData);
      this.tableData = searchData;
    },
    open(row) {
      console.log(JSON.parse(JSON.stringify(row.detail)))
      this.selectedRow = row
      // TODO
      this.iconFormVisible = true
    },
    handleSizeChange(size) {
      this.pageSize = size
      console.log(this.pagesize)
    },
    handleCurrentChange(currentPage) {
      this.currentPage = currentPage;
      console.log(this.currentPage)
    }
  },
  mounted() {
    // 填充数据
    axios.get("http://localhost:8080/api/recipe/all/")
        .then(response => {
          console.log(response.data)
          this.tableData = []
          this.tableData = response.data
        })
        .catch(error => {
          console.log(error.message);
          alert(error.message);
        })
  }
};
</script>
