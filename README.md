# spring-mybatis-example docker 
## docker ##
  - install
    ```
    https://docs.docker.com/engine/install/debian/
    ```
  - fix error iptables when start
    ```
    sudo update-alternatives --set iptables /usr/sbin/iptables-legacy
    sudo update-alternatives --set ip6tables /usr/sbin/ip6tables-legacy
    ```
  - start docker
    ```
    sudo service docker start
    sudo chmod 666 /var/run/docker.sock
    ```

## portainer community edition ##
  - xóa container đã tạo
    ```
    sudo docker rm --force portainer
    ```
  - install
    ```
    sudo docker run -d -p 8030:9000 --name portainer --restart=always -v /var/run/docker.sock:/var/run/docker.sock -v /var/portainer_data:/data portainer/portainer-ce:latest
    ```
  - thông tin truy cập
    ```
    http://localhost:8030
    ```

## gitbutket ##
  - xóa container đã tạo
    ```
    sudo docker rm --force gitbucket
    ```
  - cài đặt gitbutket
    ```
    sudo docker run -d -p 8040:8080 --name gitbucket --restart=on-failure -v /var/gitbucket:/gitbucket gitbucket/gitbucket
    ```
  - thông tin truy cập
    ```
    http://localhost:8040
    ```

## jenkins ##
  - tạo thư mục chứa setting của jenkins
    ```
    sudo mkdir /var/jenkins_home
    ```
  - cấp quyền truy cập
    ```
    sudo chmod a=rwx /var/jenkins_home
    ```
  - lấy thông tin docker-compose để gán vào jenkins
    ```
    dpkg -L docker-compose-plugin
    ```
  - cài đặt jenkins
    ```
    sudo docker run -d -p 8050:8080 --name jenkins --restart=on-failure -v /var/run/docker.sock:/var/run/docker.sock -v $(which docker):$(which docker) -v /usr/libexec/docker/cli-plugins/docker-compose:/usr/libexec/docker/cli-plugins/docker-compose -v /var/jenkins_home:/var/jenkins_home jenkins/jenkins:lts
    ```
  - lấy thông tin admin password
    ```
    sudo docker exec jenkins cat /var/jenkins_home/secrets/initialAdminPassword
    ```
  - truy cập bash của jenkins
    ```
    sudo docker exec -it jenkins bash
    ```
  - thông tin URL của gitbutket sử dụng trong jenkins
    portainer => networks => bridge => 172.17.0.1 not localhost
  - thông tin truy cập
    ```
    http://localhost:8050
    ```
## deploy ##
  - tham khảo cài đặt ở `docker-compose.yml`
  - xoá thư mục data của mysql
    ```
    sudo rm -r /var/mysql-data/
    ```
  - tạo job mới ở jenkins
    - New Item -> Freestyle project
    - Configure
      - Source Code Management
        - Git
          - Repository URL #lấy IP của gitbutket ở portainer => networks => bridge => 172.17.0.1 (không phải localhost)
            ```
            http://172.17.0.1:8040/git/quyentb/spring-mybatis-example.git
            ```
            hoặc
            ```
            https://github.com/tbquyen/spring-mybatis-example.git
            ```
          - Branch Specifier
            ```
            */main
            ```
    - Build Steps
      - Add build step
        - Execute shell
          - Command
            ```
            echo STEP1: build maven project
            echo "-v /usr/share/maven/.m2:/root/.m2" thông tin repository sẽ được lưu ở "/usr/share/maven/.m2"
            echo "-v ${WORKSPACE}:/usr/src/maven-project" gắn thư mục project vào thư mục "/usr/src/maven-project"
            echo "-w /usr/src/maven-project" chọn workspace để chạy maven
            docker run --rm --name maven-build-project -v /usr/share/maven/.m2:/root/.m2 -v ${WORKSPACE}:/usr/src/maven-project -w /usr/src/maven-project maven:3.8.7-openjdk-18-slim mvn clean package

            echo STEP2: build docker image
            docker compose down
            docker rmi -f spring-mybatis-example:tomcat
            docker build -t spring-mybatis-example:tomcat .

            echo STEP3: deploy docker image
            docker compose up -d
            ```
