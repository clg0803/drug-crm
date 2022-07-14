<template>
  <div id="app">
    <el-container style="height: 700px; border: 1px solid #eee">

      <el-container>
        <el-header style="text-align: center; font-size: 30px">
          <span> 开处方界面 </span>
        </el-header>

        <el-header style="text-align: right; font-size: 12px">
          <el-input
              v-model="searchValue" size="mini" clearable
              placeholder="请输入药品名称查询" style="width:200px"></el-input>
          <el-button type="primary" size="mini" @click="doFilter">查询</el-button>
          <span> {{ docType }} </span>
        </el-header>

        <el-main>
          <el-button type="success" size="normal" icon="el-icon-upload" @click="uploadItems">查看完备处方并提交</el-button>
          <el-button type="warning" size="small" icon="el-icon-delete" @click="resetAll">清空处方</el-button>
          <el-table :data="tableData.slice((currentPage-1)*pageSize, currentPage*pageSize)">
            <el-table-column prop="drugID" label="药品ID" width="100" align="center">
            </el-table-column>
            <el-table-column prop="drugName" label="药品名字" width="200" align="center">
            </el-table-column>
            <el-table-column prop="description" label="药品描述" width="300" style="text-align: center" align="center">
            </el-table-column>
            <el-table-column prop="drugClass" label="药品类型" width="100" align="center">
            </el-table-column>
            <el-table-column prop="inventory" label="药品库存" width="100" align="center">
            </el-table-column>
            <el-table-column prop="unit" label="药品单位" width="100" align="center">
            </el-table-column>

            <el-table-column label="操作" align="center">
              <template slot-scope="scope">
                <el-button size="mini" type="primary" icon="el-icon-mouse" @click="addToItems(scope.row, scope.$index)"
                           :disabled="getOptional(scope.$index)">
                  加入处方中
                </el-button>
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

    <el-dialog
        title="处方清单"
        :visible.sync="dialogVisible"
        width="30%"
    >
      <div>
        <table style="margin: auto;">
          <tr>
            <td>药品名称</td>
            <td> 用量</td>
          </tr>
          <tr v-for="(k, v) in this.items">
            <td>{{ k[0] }}</td>
            <td>{{ k[1] }}</td>
          </tr>
        </table>
      </div>
      <span slot="footer" class="dialog-footer">
    <el-button @click="dialogVisible = false"> 取消 </el-button>
    <el-button type="primary" @click="submitItems()"> 确定提交 </el-button>
  </span>
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
export default {
  data() {
    return {
      currentPage: 1,
      pageSize: 5,
      tableData: [],
      searchValue: "",
      docType: "",
      items: new Map(),
      optional: 0,
      dialogVisible: false,
    }
  },
  methods: {
    doFilter() {
      console.log("into doFilter() method ... ")
      console.log(this.searchValue)
      // TODO
      let searchData = [];
      this.tableData.filter((item) => {
        if (item.drugName.search(this.searchValue) != -1) {
          searchData.push(item)
        }
      })
      console.log(searchData);
      this.tableData = searchData;
    },
    addToItems(row, index) {
      console.log(row.drugName)
      this.$prompt(`请输入 ${row.drugName} 的用量`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      }).then(({value}) => {
        value = parseInt(value);
        console.log(value)

        if (!value || value <= 0 || value > row.inventory) {
          this.$message({
            type: 'error',
            message: '输入的数据范围有误'
          });
          return false;
        } else {
          this.handleDisableButton(index);
          this.items.set(row.drugName, value);
          console.log(this.items);
        }

        this.$message({
          type: 'success',
          message: '加入成功'
        });
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '取消加入'
        });
      });
    },
    // handle disable buttons
    handleDisableButton(index) {
      const idx = (this.currentPage - 1) * this.pageSize + index;
      console.log("disable button indexed AT : " + idx);
      this.optional |= 1 << (idx);
    },
    getOptional(index) {
      const idx = (this.currentPage - 1) * this.pageSize + index;
      return !!(this.optional >> idx & 1 !== 0);
    },
    resetAll() {
      console.log("resetAll() ...")
      this.optional = 0;
      this.items.clear();
    },
    uploadItems() {
      console.log("uploadItems() ... ");
      console.log(this.items);
      this.dialogVisible = true;
    },
    submitItems() {
      this.dialogVisible = false;
      let that = this;
      console.log(this.items);

      // 按照类型填充数据
      /**
       * {
       *     "人参": 3,
       *     "陈皮": 888,
       *     "炉甘石洗剂": 999
       * }
       */

      let recipeItems = {};
      for(let [k,v] of this.items.entries()){
        recipeItems[k] = v;
      }
      console.log(recipeItems);
      // submit
      axios.post('http://localhost:8080/api/recipe/add/',
          recipeItems
      ).then(function (response) {
        console.log(response);
        if (response.data === true) {
          that.$confirm("创建处方成功", "提示", {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: 'success',
          }).then(() => {
            location.reload();
          });
        } else {
          that.$confirm("创建失败 请联系管理员", "提示", {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: 'warning',
          }).then(() => {
            location.reload();
          });
        }
      }).catch(function (error) {
        console.log(error);
        that.$confirm("发生未知错误", "提示", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: 'error',
        }).then(() => {
          location.reload();
        });
      })
      this.items.clear();
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
    this.$axios.get("http://localhost:8080/api/drug/home/all/")
        .then(response => {
          if (response.data.code === 0) {
            console.log(response.data.data);
            this.tableData = [];
            this.tableData = response.data.data;
            let ident = response.data.docType;
            switch (ident) {
              case 1:
                this.docType = '西医';
                break;
              case 2:
                this.docType = '中医';
                break;
              case 3:
                this.docType = '管理员';
                break;
            }
          }
        })
        .catch(error => {
          console.log(error.message);
          alert(error.message);
        })
  }
};
</script>
