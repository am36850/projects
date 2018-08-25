delete n1
from nse_per_day_data n1 , nse_per_day_data n2
where n1.id > n2.id
and n1.DATE = n2.date
and n1.ticker = n2.ticker;