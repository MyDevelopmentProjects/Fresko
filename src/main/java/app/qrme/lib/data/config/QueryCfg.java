package app.qrme.lib.data.config;

import app.qrme.lib.utils.BaseConstants;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QueryCfg {
    private String field;
    private BaseConstants.QueryOperation operation;
    private BaseConstants.QueryOperation nextOperation;
}