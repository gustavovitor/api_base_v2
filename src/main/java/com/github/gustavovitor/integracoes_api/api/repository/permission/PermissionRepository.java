package com.github.gustavovitor.integracoes_api.api.repository.permission;

import com.github.gustavovitor.integracoes_api.api.domain.auth_user.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {}
