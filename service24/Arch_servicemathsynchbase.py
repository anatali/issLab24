### conda install diagrams
from diagrams import Cluster, Diagram, Edge
from diagrams.custom import Custom
import os
os.environ['PATH'] += os.pathsep + 'C:/Program Files/Graphviz/bin/'

graphattr = {     #https://www.graphviz.org/doc/info/attrs.html
    'fontsize': '22',
}

nodeattr = {   
    'fontsize': '22',
    'bgcolor': 'lightyellow'
}

eventedgeattr = {
    'color': 'red',
    'style': 'dotted'
}
with Diagram('servicemathsynchbaseArch', show=False, outformat='png', graph_attr=graphattr) as diag:
  with Cluster('env'):
     sys = Custom('','./qakicons/system.png')
### see https://renenyffenegger.ch/notes/tools/Graphviz/attributes/label/HTML-like/index
     with Cluster('ctxservice', graph_attr=nodeattr):
          caller_1=Custom('caller_1','./qakicons/symActorSmall.png')
          servicemathcoded=Custom('servicemathcoded(coded)','./qakicons/codedQActor.png')
     caller_1 >> Edge(color='magenta', style='solid', decorate='true', label='<dofibo<font color="darkgreen"> fibodone</font> &nbsp; >',  fontcolor='magenta') >> servicemathcoded
diag
